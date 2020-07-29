package com.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.client.crossplatform.FileReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Log.i("WJZ", externalCacheDir!!.absolutePath)
    val reader = FileReader(externalCacheDir!!.absolutePath + "/1.txt")
    val buffSize = 1024
    var buffer = ByteArray(buffSize)
    var readSize = 0
    val stringBuilder = StringBuilder()
    while((reader.read(buffer, buffSize).also { readSize = it } != -1)) {
      val segment = java.lang.String(buffer, 0, readSize)
      stringBuilder.append(segment)
    }
    Log.i("WJZ", "Content $stringBuilder")
  }
}
