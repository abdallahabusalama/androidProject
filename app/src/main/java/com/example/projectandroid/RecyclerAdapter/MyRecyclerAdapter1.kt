package com.example.projectandroid.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectandroid.R
import com.example.projectandroid.admin.ProductDetails
import kotlinx.android.synthetic.main.rec2.view.*

class MyRecyclerAdapter1(val context: Context, val list: ArrayList<MyModel1>) : RecyclerView.Adapter<MyRecyclerAdapter1.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name = item.name
        var price = item.price
        var datils = item.desc
        var rate = item.rate
        var image = item.imageproduct
        var item_datils = item.item_datils
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.rec2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.price.text = list[position].price
        holder.datils.text = list[position].description
        holder.rate.text = list[position].rate
        Glide.with(context).load(list[position].image).into(holder.image)
        holder.item_datils.setOnClickListener {
            var intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("productId",list[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
