#include "Reader.h"
#include <fcntl.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

Reader::Reader(const char *filePath) {
    _fd = open(filePath, O_RDONLY);
}

Reader::~Reader() {
    if (_fd > 0) {
        close(_fd);
    }
}

int Reader::readData(char *buffer, int size) {
    if (_fd < 0) {
        return -1;
    } else {
        return read(_fd, buffer, size);
    }
}