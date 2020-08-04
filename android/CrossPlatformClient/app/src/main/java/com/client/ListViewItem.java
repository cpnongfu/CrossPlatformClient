package com.client;

import android.util.Log;
import android.view.View;

public class ListViewItem implements Runnable {
  private static final String TAG = "ListViewAdapter";
  private static final boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
  public String mTitle;
  public int mImageId;
  public String mActivityName;
  public boolean mVisible;
  public View mBindedView;

  public ListViewItem(String title, int imageId, String name, int pos) {
    mTitle = pos + " " + title;
    mImageId = imageId;
    mActivityName = name;
    mVisible = false;
  }

  protected void report() {
    if (DEBUG) {
      Log.w(TAG, "ListViewItem: mTitle = " + mTitle);
    }
  }

  @Override
  public void run() {
    report();
  }
}