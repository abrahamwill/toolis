cmake_minimum_required(VERSION 3.4.1)
add_library(
        cpp_code
        SHARED
        cpp_code.cpp)

find_library(
        log-lib
        log)

target_link_libraries(
        cpp_code
        ${log-lib})
