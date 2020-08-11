package com.client

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListViewAdapter(private val mContext: Context, private val mItems: Array<ListViewItem>?) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var enable = false
    override fun getCount(): Int {
        return mItems?.size ?: 0
    }

    override fun getItem(pos: Int): Any? {
        return if (mItems != null && mItems.isNotEmpty()) {
            mItems[pos]
        } else null
    }

    override fun getItemId(postion: Int): Long {
        return postion.toLong()
    }

    private inner class ViewHolder {
        var mContent: TextView? = null
        var mImage: ImageView? = null
        var mActivity: String? = null
    }

    override fun getView(pos: Int, v: View?, arg2: ViewGroup): View? {
        var view = v
        if (mItems == null || mItems.isEmpty()) {
            return null
        }
        val holder: ViewHolder
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item, null)
            holder = ViewHolder()
            holder.mContent = view.findViewById(R.id.item_text_view)
            holder.mImage = view.findViewById(R.id.item_image_view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.mImage!!.setImageResource(mItems[pos].mImageId)
        holder.mContent!!.text = mItems[pos].mTitle
        holder.mActivity = mItems[pos].mActivityName
        view?.setOnClickListener {
            if (!enable) return@setOnClickListener
            val activityName = (it.tag as ViewHolder).mActivity
            Log.v(TAG, "ListViewAdapter.getView: activityName = $activityName")
            if (!TextUtils.isEmpty(activityName)) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setClassName(mContext.packageName, ACTIVITY_NAME_PREFIX + activityName)
                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to start activity: $activityName, reason: $e"
                    )
                }
            }
        }
        mItems[pos].mBindedView = view
        return view
    }

    fun setEnable(enable: Boolean) {
        this.enable = enable
    }
}