package com.example.derongliu.kt

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.derongliu.androidtest.R
import kotlinx.android.synthetic.main.kt_item.view.*

class KtActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_kt)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = MyAdapter(initData())
        //Toast.makeText(applicationContext, "hehe", Toast.LENGTH_LONG).show()
    }

    fun initData(): ArrayList<String> {
        val data: ArrayList<String> = arrayListOf()
        data.add("main")
        data.add("one")
        data.add("two")
        data.add("three")
        data.add("four")
        return data
    }


    private class MyAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<MyHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return MyHolder(LayoutInflater.from(p0.context).inflate(R.layout.kt_item, p0, false))
        }

        override fun getItemCount(): Int {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            return items.count()
        }

        override fun onBindViewHolder(p0: MyHolder, p1: Int) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            p0.tv.text = items[p1]
            p0.tv.setOnClickListener { Toast.makeText(p0.tv.context, "hehe", Toast.LENGTH_LONG).show() }

        }
    }


    private class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView

        init {
            tv = view.kt_item_tv;
        }
    }
}
