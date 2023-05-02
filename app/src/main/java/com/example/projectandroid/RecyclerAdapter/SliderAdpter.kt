package com.example.projectandroid.RecyclerAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.projectandroid.R
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdpter internal constructor(
        slideritem: MutableList<SliderItem>,
        viewpager: ViewPager2
) : RecyclerView.Adapter<SliderAdpter.SliderViewHolder>() {

    private val SliderItem : List<SliderItem>
    init {
        this.SliderItem=slideritem
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageslider)
        fun images(slideritem: SliderItem) {
            imageView.setImageResource(slideritem.images)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_image,parent,false))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.images(SliderItem[position])
    }

    override fun getItemCount(): Int {
        return  SliderItem.size
    }
}