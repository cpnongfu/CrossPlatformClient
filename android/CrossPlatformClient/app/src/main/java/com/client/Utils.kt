package com.client

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.annotation.IdRes

const val TAG = "ClientDemo"
const val ACTIVITY_NAME_PREFIX = "com.client."
var DEBUG = Log.isLoggable(TAG, Log.VERBOSE)
fun <T: View> Activity._view(@IdRes id: Int): T {
    return findViewById<T>(id)
}

class X {
    companion object {
        fun Y.test() = "I'm Y.test"
    }
    fun  Y.foo() = "I'm Y.foo"
}

class Y {
}

fun Y.func() = "I'm Y.func"