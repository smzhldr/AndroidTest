package com.example.derongliu.widget.scrollmenu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.derongliu.androidtest.R;

import java.util.ArrayList;

public class ScrollMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_menu);
        ScrollMenuBar menuBar = (ScrollMenuBar) findViewById(R.id.scroll_menu_bar);

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Color.RED);
        arrayList.add(Color.GRAY);
        arrayList.add(Color.GREEN);
        arrayList.add(Color.DKGRAY);
        arrayList.add(Color.LTGRAY);
        arrayList.add(Color.MAGENTA);
        arrayList.add(Color.RED);
        arrayList.add(Color.BLACK);
        arrayList.add(Color.BLUE);
        arrayList.add(Color.YELLOW);

        menuBar.setMenuItems(arrayList);
    }
}
