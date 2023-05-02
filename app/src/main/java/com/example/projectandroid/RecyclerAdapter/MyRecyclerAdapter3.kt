package com.example.projectandroid.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectandroid.Product_Details_User
import com.example.projectandroid.R
import com.example.projectandroid.admin.ProductDetails
import kotlinx.android.synthetic.main.rec2.view.*
import kotlinx.android.synthetic.main.rec3.view.*

class MyRecyclerAdapter3(val context: Context, val list: ArrayList<MyModel1>) :
    RecyclerView.Adapter<MyRecyclerAdapter3.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name = item.textView14
        var price = item.textView16
        var datils = item.textView15
        var rate = item.textView17
        var image = item.imageView7
        var item_datils = item.asd

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.rec3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.price.text = list[position].price
        holder.datils.text = list[position].description
        holder.rate.text = list[position].rate
        Glide.with(context).load(list[position].image).into(holder.image)
        holder.item_datils.setOnClickListener {
            var intent = Intent(context, Product_Details_User::class.java)
            intent.putExtra("productId", list[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
