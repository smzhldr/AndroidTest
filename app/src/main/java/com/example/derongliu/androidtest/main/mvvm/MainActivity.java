package com.example.derongliu.androidtest.main.mvvm;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.derongliu.androidtest.R;
import com.example.derongliu.androidtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        binding.setViewModel(mainViewModel);
        binding.setLifecycleOwner(this);
    }
}