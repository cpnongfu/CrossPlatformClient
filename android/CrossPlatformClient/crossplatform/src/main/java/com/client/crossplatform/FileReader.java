package com.client.crossplatform;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class FileReader {
    static {
        System.loadLibrary("clientdemo");
    }
    private String mFilePath;
    private int mFd;
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
        mFd = nativeOpen(path);
    }

    public void close() throws IOException {
        nativeClose(mFd);
    }

    public int read(@NonNull byte[] buffer, int size) throws IOException {
        int realSize = nativeRead(mFd, buffer, size);
        if (realSize == 0) return -1;
        return realSize;
    }

    private native int nativeOpen(@NonNull String path);
    private native int nativeRead(int fd, byte[] buffer, int size);
    private native void nativeClose(int fd);
}
