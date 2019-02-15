package com.example.derongliu.ndk;

public class Person {
    public String names;
    public String sex;
    public int age;

    public Person() {
    }

    public Person(String names, String sex, int age) {
        this.names = names;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" +
                names +
                "\n" +
                "sex:" +
                sex +
                "\n" +
                "age:" +
                String.valueOf(age);
    }

    public void sayHello() {
        names = "My Name is:" + names;
    }
}
