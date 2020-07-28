#include "jni_utils.h"
#include <android/log.h>
#include <vector>


jclass getJStringClass(JNIEnv *env) {
    static jclass cls = nullptr;
    if (cls) {
        return cls;
    }
    jclass clazz = env->FindClass("java/lang/String");
    if (clazz) {
        cls = (jclass)env->NewGlobalRef(clazz);
    }
    return cls;
}

jobjectArray convertVectorStringToArray(JNIEnv *env, std::shared_ptr<std::vector<std::string>> vector) {
    CHECK_RETURN_NULL(vector)
    CHECK_RETURN_NULL(vector->size() > 0)
    jclass jStringClass = getJStringClass(env);
    CHECK_RETURN_NULL(jStringClass)
    jobjectArray jarray = env->NewObjectArray(vector->size(), jStringClass, nullptr);
    CHECK_RETURN_NULL(jarray)
    // TODO: Local Reference leak risk
    for (int i = 0; i < vector->size(); i++) {
        std::string str = (*vector)[i];
        jstring jstr = env->NewStringUTF(str.c_str());
        env->SetObjectArrayElement(jarray, i, jstr);
    }
    return jarray;
}

std::shared_ptr<std::string> convertJStringToString(JNIEnv *env, jstring jstr) {
    std::shared_ptr<std::string> result;
    CHECK_RETURN_RESULT(jstr, result)
    const char* cstr = env->GetStringUTFChars(jstr, nullptr);
    CHECK_RETURN_RESULT(cstr, result)
    result = std::make_shared<std::string>(cstr);
    env->ReleaseStringUTFChars(jstr, cstr);
    return result;
}



