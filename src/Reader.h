#ifndef _READER_H
#define _READER_H
class Reader {
public:
    Reader(const char* filePath);
    virtual ~Reader();
    int readData(char* buffer, int size);
private:
    int _fd = -1;
};
#endif