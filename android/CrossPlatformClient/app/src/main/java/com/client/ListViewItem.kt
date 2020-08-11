package com.client

import android.util.Log
import android.view.View

class ListViewItem(title: String, imageId: Int, name: String, pos: Int) : Runnable {
    var mTitle: String? = null
    var mImageId: Int = 0
    var mActivityName: String? = null
    var mVisible: Boolean = false
    var mBindedView: View? = null
    private fun report() {
        if (DEBUG) {
            Log.w(TAG, "ListViewItem: mTitle = $mTitle")
        }
    }

    override fun run() {
        report()
    }

    init {
        mTitle = "$pos $title"
        mImageId = imageId
        mActivityName = name
        mVisible = false
    }
}