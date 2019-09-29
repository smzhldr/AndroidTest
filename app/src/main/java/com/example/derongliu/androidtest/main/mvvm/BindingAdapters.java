package com.example.derongliu.androidtest.main.mvvm;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter({"items", "itemListener"})
    public static void setAdapter(RecyclerView recyclerView, List<String> items, OnItemClickListener listener) {
        if (recyclerView.getAdapter() == null) {
            MainAdapter adapter = new MainAdapter(items);
            adapter.setListener(listener);
            recyclerView.setAdapter(adapter);
        } else {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter instanceof MainAdapter) {
                    ((MainAdapter) adapter).setItems(items);
                    adapter.notifyDataSetChanged();
                }
        }
    }
}
