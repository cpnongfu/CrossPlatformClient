#include "NativeRequest.h"

NativeRequest::NativeRequest(jobject jrequest): _requestObj(jrequest) {}

NativeRequest::~NativeRequest() {}

std::shared_ptr<RequestResult> NativeRequest::doRequest() {
    return std::shared_ptr<RequestResult>();
}