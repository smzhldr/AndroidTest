//
// Created by derong.liu on 2019/2/13.
//
#include "com_example_derongliu_ndk_NDKTest.h"
#include <jni.h>
#include <string.h>
#include <stdlib.h>

jstring Java_com_example_derongliu_ndk_NDKTest_sayHello(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("Hello World");
}

jstring Java_com_example_derongliu_ndk_NDKTest_sayHelloObject(JNIEnv *env, jobject instance,
                                                              jobject person) {

    jclass jPerson = env->FindClass("com/example/derongliu/ndk/Person");
    //jclass jPerson = env->GetObjectClass(person);

    if (jPerson == NULL) {
        return env->NewStringUTF("null");
    }

    jfieldID nameID = env->GetFieldID(jPerson, "names", "Ljava/lang/String;");
    jfieldID ageID = env->GetFieldID(jPerson, "age", "I");
    jfieldID sexID = env->GetFieldID(jPerson, "sex", "Ljava/lang/String;");

    jstring name = (jstring) (env->GetObjectField(person, nameID));
    jint age = env->GetIntField(person, ageID);
    jstring sex = (jstring) (env->GetObjectField(person, sexID));

    const char *cName = env->GetStringUTFChars(name, 0);
    const char *cSex = env->GetStringUTFChars(sex, 0);

    int length = strlen(cName) + strlen(cSex) + 2;

    //char *res = malloc(sizeof(char)*length);
    char *res = new char[length + 20];

    strcpy(res, "name:");
    strcat(res, cName);
    strcat(res, "\n");

    strcat(res, "sex:");
    strcat(res, cSex);
    strcat(res, "\n");

    char *cAge = new char[2];
    sprintf(cAge, "%d", (int) age);
    strcat(res, "age:");
    strcat(res, cAge);

    env->ReleaseStringUTFChars(name, cName);
    env->ReleaseStringUTFChars(sex, cSex);

    return env->NewStringUTF(res);
}

jobject JNICALL Java_com_example_derongliu_ndk_NDKTest_getPerson(JNIEnv *env, jobject instance) {

    jclass person = env->FindClass("com/example/derongliu/ndk/Person");

    if (person == NULL) {
        return NULL;
    }

    jmethodID constroctID = env->GetMethodID(person, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;I)V");

    jstring name = env->NewStringUTF("lingling");
    jstring sex = env->NewStringUTF("femael");

    jobject jPerson = env->NewObject(person, constroctID, name, sex, 27);

    jmethodID methodID = env->GetMethodID(person, "sayHello", "()V");
    if (methodID != NULL) {
        env->CallVoidMethod(jPerson, methodID);
    }

    return jPerson;
}