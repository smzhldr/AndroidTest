package com.example.derongliu.ndk;

public class NDKTest {

    static {
        System.loadLibrary("ndk_test");
    }

    public native String sayHello();

    public native String sayHelloObject(Person person);

    public native Person getPerson();
}
