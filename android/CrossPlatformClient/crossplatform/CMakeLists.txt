# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

SET(libname "clientdemo")
SET(ROOT "${CMAKE_SOURCE_DIR}/../../../")
get_filename_component(ROOT "${ROOT}" ABSOLUTE)

# jni files
SET(JNI_DIR "${ROOT}/android/CrossPlatformClient/crossplatform/src/main/cpp")
file(GLOB_RECURSE JNI_SRCS "${JNI_DIR}/*.cpp")
#MESSAGE(WARNING "###### JNI SRC: ${JNI_SRCS}")
INCLUDE_DIRECTORIES(${JNI_DIR})

# common source and include
SET(CXX_DIR "${ROOT}/src")
file(GLOB_RECURSE CXX_SRC "${CXX_DIR}/*.cpp")
INCLUDE_DIRECTORIES(${CXX_DIR})
MESSAGE(STATUS "###### CXX_SRC: ${CXX_SRC}")

# all src files
SET(SRC "${JNI_SRCS};${CXX_SRC}")
MESSAGE(STATUS "###### SRC: ${SRC}")
MESSAGE(STATUS "###################")

add_library(${libname}
             SHARED
             ${SRC})


find_library(log-lib
              log )

target_link_libraries(${libname}
                       ${log-lib} )