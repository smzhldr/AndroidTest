package com.example.derongliu.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.derongliu.androidtest.R;

public class NDKTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndktest);
        TextView btn = (TextView) findViewById(R.id.ndk_test_btn);
        Person person = new Person();
        person.names = "derong";
        person.age = 28;
        person.sex = "man";
        //btn.setText(new NDKTest().sayHello());

        NDKTest ndkTest = new NDKTest();

        String res = ndkTest.sayHelloObject(person);
        res += "\n";
        res += ndkTest.getPerson().toString();
        btn.setText(res);
    }
}
