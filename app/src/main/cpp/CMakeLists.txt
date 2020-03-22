
cmake_minimum_required(VERSION 3.4.1)

include_directories(bzip)

file(GLOB bzip bzip/*.c)

add_library(
        native-lib

        SHARED

        native-lib.cpp
        bspatch.c
        ${bzip})


find_library(
        log-lib

        log)

target_link_libraries(
        native-lib

        ${log-lib})