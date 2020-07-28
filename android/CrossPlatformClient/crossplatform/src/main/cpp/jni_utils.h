#ifndef _JNI_UTILS_H
#define _JNI_UTILS_H
#include <jni.h>
#include <android/log.h>
#include <string>
#include <memory>

#define TAG "CrossPlatform"

#define LOGE(format, ...) __android_log_print(ANDROID_LOG_ERROR, TAG, format, ##__VA_ARGS__)
#define LOGI(format, ...) __android_log_vprint(ANDROID_LOG_INFO, TAG, format, ##__VA_ARGS__)
#define LOGD(format, ...) __android_log_vprint(ANDROID_LOG_DEBUG, TAG, format, ##__VA_ARGS__)
#define LOGW(format, ...) __android_log_vprint(ANDROID_LOG_WARN, TAG, format, ##__VA_ARGS__)

#define CHECK_RETURN_NULL(ptr) \
if(!(ptr)) { \
    LOGE(#ptr " is false or null or zero, %s:%d", __FUNCTION__, __LINE__); \
    return 0; \
};

#define CHECK_RETURN(ptr) \
if(!(ptr)) { \
    LOGE(#ptr " is false or null or zero, %s:%d", __FUNCTION__, __LINE__); \
    return; \
};

#define CHECK_RETURN_RESULT(ptr, result) \
if(!(ptr)) { \
    LOGE(#ptr " is false or null or zero, %s:%d", __FUNCTION__, __LINE__); \
    return result; \
};


jclass getJStringClass(JNIEnv *env);
jobjectArray convertVectorStringToArray(JNIEnv *env, std::shared_ptr<std::vector<std::string>> vector);
std::shared_ptr<std::string> convertJStringToString(JNIEnv *env, jstring jstr);

#endif // _JNI_UTILS_H
