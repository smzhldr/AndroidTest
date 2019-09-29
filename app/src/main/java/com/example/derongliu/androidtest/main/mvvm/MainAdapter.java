package com.example.derongliu.androidtest.main.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.derongliu.androidtest.R;
import com.example.derongliu.androidtest.databinding.MainItemBinding;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.DataBindingViewHolder> {

    private List<String> items;
    private OnItemClickListener listener;


    public MainAdapter(List<String> strings) {
        this.items = strings;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_item, viewGroup, false);
        return new DataBindingViewHolder<MainItemBinding>(inflate, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, final int i) {
        ViewDataBinding dataBinding = holder.getDataBinding();
        if (dataBinding instanceof MainItemBinding) {
            ((MainItemBinding) dataBinding).setDataBindingViewHolder(holder);
            ((MainItemBinding) dataBinding).setItemString(items.get(i));
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public T getDataBinding() {
            return dataBinding;
        }

        T dataBinding;
        private OnItemClickListener listener;

        public DataBindingViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dataBinding = DataBindingUtil.bind(itemView);
            this.listener = listener;
        }

        public void onItemClick() {
            listener.onItemClick(itemView.getContext(), getLayoutPosition());
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
