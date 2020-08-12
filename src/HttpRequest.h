#ifndef CLIENT_HTTP_REQUEST_H
#define CLIENT_HTTP_REQUEST_H

#include <memory>

struct RequestResult {
    int httpCode = 0;
    const char* httpBody = nullptr;
    int httpBodySize = 0;

};

class HttpRequest {
public:
    virtual std::shared_ptr<RequestResult> doRequest() = 0;
    virtual ~HttpRequest() {}
};


#endif //CLIENT_HTTP_REQUEST_H
