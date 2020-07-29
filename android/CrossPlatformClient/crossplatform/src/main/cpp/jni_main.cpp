//
// Created by Joe Wu on 2020/6/18.
//
#include <jni.h>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h>
#include "jni_utils.h"

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jint JNICALL
Java_com_client_crossplatform_FileReader_nativeOpen
        (JNIEnv *env, jobject thiz, jstring jpath) {
    auto path = convertJStringToString(env, jpath);
    int fd = open(path->c_str(), O_RDONLY);
    CHECK_RETURN_NULL(path);
    return fd;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_client_crossplatform_FileReader_nativeRead
        (JNIEnv *env, jobject thiz, jint fd, jbyteArray jbuffer, jint size) {
    CHECK_RETURN_NULL(fd)
    char* buffer = new char[size];
    auto realSize = read(fd, buffer, size);
    if (realSize > 0) {
        env->SetByteArrayRegion(jbuffer, 0, realSize, (const jbyte*)buffer);
    }
    delete []buffer;
    return realSize;
}

JNIEXPORT void JNICALL
Java_com_client_crossplatform_FileReader_nativeClose
    (JNIEnv *env, jobject thiz, jint fd) {
    CHECK_RETURN(fd)
    close(fd);
}

#ifdef __cplusplus
}
#endif
