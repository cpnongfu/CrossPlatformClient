//
// Created by Joe Wu on 2020/6/18.
//
#include <jni.h>
#include <android/log.h>
#include "jni_utils.h"

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jlong JNICALL
Java_com_client_crossplatform_FileReader_nativeOpen
        (JNIEnv *env, jobject thiz, jstring jpath) {
    auto path = convertJStringToString(env, jpath);
    FILE* file = fopen(path->c_str(), "r");
    CHECK_RETURN_NULL(path);
    return reinterpret_cast<jlong>(file);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_client_crossplatform_FileReader_nativeRead
        (JNIEnv *env, jobject thiz, jlong ptr, jbyteArray jbuffer, jint size) {
    FILE* file = reinterpret_cast<FILE*>(ptr);
    CHECK_RETURN_NULL(file)
    char* buffer = new char[size];
    auto realSize = fread(buffer, size, size, file);
    if (realSize > 0) {
        env->SetByteArrayRegion(jbuffer, 0, realSize, (const jbyte*)buffer);
    }
    delete []buffer;
    return realSize;
}

JNIEXPORT void JNICALL
Java_com_client_crossplatform_FileReader_nativeClose
    (JNIEnv *env, jobject thiz, jlong ptr) {
    FILE* file = reinterpret_cast<FILE*>(ptr);
    CHECK_RETURN(file)
    fclose(file);
}

#ifdef __cplusplus
}
#endif
