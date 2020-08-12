#ifndef __NATIVE_REQUEST_H_
#define __NATIVE_REQUEST_H_
#include "HttpRequest.h"
#include <jni.h>
class NativeRequest : public HttpRequest {
public:
    NativeRequest(jobject jrequest);
    virtual ~NativeRequest();
    virtual std::shared_ptr<RequestResult> doRequest();
private:
    jobject _requestObj;
};
#endif