package com.client

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {
  private val mListView: ListView by lazy { findViewById<ListView>(R.id.main_list) }
  private val adapter by lazy { ListViewAdapter(this, getListViewItems())}
  private var hasPermission = false
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
//    mListView.adapter = ListViewAdapter(this, getListViewItems())
    Log.i("WJZ", externalCacheDir!!.absolutePath)
    findViewById<ListView>(R.id.main_list).adapter = adapter
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


  override fun onResume() {
    super.onResume()
    MainActivityPermissionsDispatcher.onPermissionGrantedWithCheck(this)
  }

  private fun getListViewItems(): Array<ListViewItem>? {
    val itemNames = resources.getStringArray(R.array.demo_item_names)
    val activities = resources.getStringArray(R.array.demo_item_activities)
    if (itemNames.isNotEmpty() && activities.isNotEmpty()) {
      if (itemNames.size != activities.size) {
        return null
      }
      return Array(itemNames.size) {
        ListViewItem(itemNames[it], R.mipmap.ic_launcher, activities[it], it)
      }
    }
    return null
  }

  @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
  fun onShowPermission(request: PermissionRequest) {
    AlertDialog.Builder(this)
      .setMessage("需要读写SDK权限")
      .setPositiveButton("下一步", DialogInterface.OnClickListener { dialog, which ->
        request.proceed() //继续执行请求
      }).setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which ->
        request.cancel() //取消执行请求
      })
      .show()
  }

  @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
  fun onPermissionDenied() {
    Log.v(TAG, "permission denied")
    hasPermission = false
    adapter.setEnable(false)
    Toast.makeText(this, "读写SD权限被拒绝", Toast.LENGTH_SHORT).show()
  }

  @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
  fun onPermissionGranted() { //权限申请成功
    Log.v(TAG, "permission granted")
    hasPermission = true
    adapter.setEnable(true)
    Toast.makeText(this, "读写SD权限被获取", Toast.LENGTH_SHORT).show()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
  }
}
