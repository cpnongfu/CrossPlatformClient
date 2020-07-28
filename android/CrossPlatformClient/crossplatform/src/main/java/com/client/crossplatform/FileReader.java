package com.client.crossplatform;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class FileReader {
    static {
        System.loadLibrary("clientdemo");
    }
    private String mFilePath;
    private long mPtr;
    public FileReader(@NonNull String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException(path + "File not exist");
        }
        if (file.isDirectory()) {
            throw new IOException(path + " is directory");
        }
        if (!file.canRead()) {
            throw new IOException("No permission to read " + path);
        }
        mFilePath = path;
        mPtr = nativeOpen(path);
    }

    public void close() throws IOException {
        nativeClose(mPtr);
    }

    public int read(byte[] buffer, int size) throws IOException {
        int realSize = nativeRead(mPtr, buffer, size);
        return realSize;
    }

    private native long nativeOpen(@NonNull String path);
    private native int nativeRead(long ptr, byte[] buffer, int size);
    private native void nativeClose(long ptr);
}
