package com.client

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.client.crossplatform.FileReader

class MainActivity : AppCompatActivity() {
  private val mListView: ListView by lazy {
    findViewById<ListView>(R.id.main_list)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mListView.adapter = ListViewAdapter(this, getListViewItems())
    Log.i("WJZ", externalCacheDir!!.absolutePath)
//    val reader = FileReader(externalCacheDir!!.absolutePath + "/1.txt")
//    val buffSize = 1024
//    var buffer = ByteArray(buffSize)
//    var readSize = 0
//    val stringBuilder = StringBuilder()
//    while((reader.read(buffer, buffSize).also { readSize = it } != -1)) {
//      val segment = java.lang.String(buffer, 0, readSize)
//      stringBuilder.append(segment)
//    }
//    Log.i("WJZ", "Content $stringBuilder")
  }

  private fun getListViewItems(): Array<ListViewItem?>? {
    val itemNames =
      resources.getStringArray(R.array.demo_item_names)
    val activities =
      resources.getStringArray(R.array.demo_item_activities)
    if (itemNames.isNotEmpty() && activities.isNotEmpty()) {
      if (itemNames.size != activities.size) {
        return null
      }
      val items = arrayOfNulls<ListViewItem>(itemNames.size)
      for (i in items.indices) {
        items[i] = ListViewItem(itemNames[i], R.mipmap.ic_launcher, activities[i], i)
      }
      return items
    }
    return null
  }
}
