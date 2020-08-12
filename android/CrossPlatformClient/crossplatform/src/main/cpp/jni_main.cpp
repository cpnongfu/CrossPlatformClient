//
// Created by Joe Wu on 2020/6/18.
//
#include <jni.h>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h>
#include "jni_utils.h"
#include "Reader.h"

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jlong JNICALL
Java_com_client_crossplatform_FileReader_nativeOpen
        (JNIEnv *env, jobject thiz, jstring jpath) {
    auto path = convertJStringToString(env, jpath);
    Reader* reader = new Reader(path->c_str());
    return reinterpret_cast<jlong>(reader);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_client_crossplatform_FileReader_nativeRead
        (JNIEnv *env, jobject thiz, jlong ptr, jbyteArray jbuffer, jint size) {
    Reader* reader = reinterpret_cast<Reader*>(ptr);
    CHECK_RETURN_NULL(ptr)
    char* buffer = new char[size];
    auto realSize = reader->readData(buffer, size);
    if (realSize > 0) {
        env->SetByteArrayRegion(jbuffer, 0, realSize, (const jbyte*)buffer);
    }
    delete []buffer;
    return realSize;
}

JNIEXPORT void JNICALL
Java_com_client_crossplatform_FileReader_nativeClose
    (JNIEnv *env, jobject thiz, jlong ptr) {
    Reader* reader = reinterpret_cast<Reader*>(ptr);
    delete reader;
}

#ifdef __cplusplus
}
#endif
