package com.example.derongliu.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.derongliu.androidtest.R;

public class NDKTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndktest);
        Button btn = (Button) findViewById(R.id.ndk_test_btn);
        btn.setText(new NDKTest().sayHello());

    }
}
