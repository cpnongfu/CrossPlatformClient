package com.client;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
  private static final String TAG = "ListViewAdapter";
  private static final boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
  private Context mContext;
  private ListViewItem[] mItems;
  private LayoutInflater mInflater;
  private final String ACTIVITY_NAME_PREFIX = "com.client.";

  public ListViewAdapter(Context ctx, ListViewItem[] items) {
    mContext = ctx;
    mItems = items;
    mInflater = LayoutInflater.from(mContext);
  }

  @Override
  public int getCount() {
    if (mItems != null) {
      return mItems.length;
    }
    return 0;
  }

  @Override
  public Object getItem(int pos) {
    if (mItems != null && mItems.length > 0) {
      return mItems[pos];
    }
    return null;
  }

  @Override
  public long getItemId(int postion) {
    return postion;
  }

  private class ViewHolder {
    TextView mContent;
    ImageView mImage;
    String mActivity;
  }

  @Override
  public View getView(int pos, View view, ViewGroup arg2) {
    if (mItems == null || mItems.length <= 0) {
      return null;
    }
    ViewHolder holder;
    if (view == null) {
      view = mInflater.inflate(R.layout.list_item, null);
      holder = new ViewHolder();
      holder.mContent = (TextView) view.findViewById(R.id.item_text_view);
      holder.mImage = (ImageView) view.findViewById(R.id.item_image_view);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    holder.mImage.setImageResource(mItems[pos].mImageId);
    holder.mContent.setText(mItems[pos].mTitle);
    holder.mActivity = mItems[pos].mActivityName;
    view.setOnClickListener(v -> {
      // TODO Auto-generated method stub
      String activityName = ((ViewHolder) v.getTag()).mActivity;
      Log.v(TAG, "ListViewAdapter.getView: activityName = " + activityName);
      if (!TextUtils.isEmpty(activityName)) {
        try {
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setClassName(mContext.getPackageName(), ACTIVITY_NAME_PREFIX
              + activityName);
          mContext.startActivity(intent);
        } catch (Exception e) {
          Log.e(TAG, "Failed to start activity: " + activityName +
              ", reason: " + e.toString());
        }
      }
    });
    mItems[pos].mBindedView = view;
    return view;
  }
}
