//
// Created by derong.liu on 2019/2/13.
//
#include "ndk_test.h"
#include <jni.h>
#include "string.h"

jstring Java_com_example_derongliu_ndk_NDKTest_sayHello(JNIEnv *env, jobject ob) {
    return env->NewStringUTF("Hello World");
}