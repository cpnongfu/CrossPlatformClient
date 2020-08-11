package com.client

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log


fun getPath(context: Context, uri: Uri): String? {
    var path: String? = null
    val scheme = uri.scheme
    // ExternalStorageProvider
    when {
        isExternalStorageDocument(uri) -> {
            Log.v(TAG, "isExternalStorageDocument")
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[1]
            Log.v(TAG, "type = $type")
            // if ("primary".equalsIgnoreCase(type)) {
            path = Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            // }
        }
        "file".equals(scheme, ignoreCase = true) -> {
            path = uri.path
        }
        "content".equals(scheme, ignoreCase = true) -> {
            val projection = arrayOf("_data")
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow("_data")
                if (cursor.moveToFirst()) {
                    path = cursor.getString(column_index)
                }
            } catch (e: Exception) { // Eat it
            } finally {
                cursor?.close()
            }
        }
    }
    return path
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}