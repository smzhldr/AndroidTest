package com.example.derongliu.kt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.derongliu.androidtest.R

class KtActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_kt)
    }



    private class MyAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
