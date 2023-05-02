package com.example.projectandroid.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectandroid.R
import com.example.projectandroid.admin.detailscategory
import com.example.projectandroid.details_Category_User
import kotlinx.android.synthetic.main.adapter.view.*


class MyRecyclerAdapter5(val context: Context, val list: ArrayList<MyModel>) : RecyclerView.Adapter<MyRecyclerAdapter5.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var imagepr = item.imagepr
        var itemcat = item.item_cat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].imagecat).into(holder.imagepr)
        holder.itemcat.setOnClickListener {
            var intent = Intent(context, details_Category_User::class.java)
            intent.putExtra("idcat", list[position].idcat)
            intent.putExtra("namecat", list[position].namecat)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}