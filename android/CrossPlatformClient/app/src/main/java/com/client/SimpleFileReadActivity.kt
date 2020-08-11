package com.client

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.client.crossplatform.FileReader
import java.io.File
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class SimpleFileReadActivity : AppCompatActivity() {
    private val openFileBtn : View by lazy { findViewById<View>(R.id.open_file_btn) }
    private val pathTv : TextView by lazy { findViewById<TextView>(R.id.file_path_tv) }
    private val contentTv : TextView by lazy { findViewById<TextView>(R.id.content_tv)}
    private var filePath : String? = null

    companion object {
        private const val FILE_SELECT_CODE = 200
        private const val FILE_PLAY_WITH_SYSTEM = 300
        private const val FILE_PLAY_WITH_VIDEOVIEW = 400
        private const val FILE_PLAY_WITH_SURFACEVIEW = 500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_file_read)
        openFileBtn.setOnClickListener { showFileChooser() }
        val path = "/sdcard/1.txt"
        Toast.makeText(this, "$path exist ? "  + File(path).exists() , Toast.LENGTH_LONG).show()

    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "No file browser found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK ) {
            filePath = data?.data?.let { getPath(this, it) }
            Log.v(TAG, "onActivityResult: path = $filePath")
            filePath?.let {
                pathTv.text = it
                readFile(it)
            }

        }
    }

    private fun readFile(filePath: String) {
        val weakContentTv = WeakReference(contentTv)
        thread {
            val reader = FileReader(filePath)
            val buffSize = 1024
            val buffer = ByteArray(buffSize)
            var readSize: Int
            val stringBuilder = StringBuilder()
            while((reader.read(buffer, buffSize).also { readSize = it } != -1)) {
                val segment = java.lang.String(buffer, 0, readSize)
                stringBuilder.append(segment)
            }
            val view = weakContentTv.get()
            view?.post {
                contentTv.text = stringBuilder.toString()
            }
        }
    }
}
