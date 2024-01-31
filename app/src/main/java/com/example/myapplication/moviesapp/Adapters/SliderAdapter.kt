package com.example.myapplication.moviesapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.moviesapp.Domain.SliderItem
import com.example.myapplication.moviesapp.R

//class for make a slider banner
class SliderAdapter(
    private val sliderItems: MutableList<SliderItem>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        return SliderViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.slideitems_container, parent, false
        ))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(sliderItem: SliderItem) {
            val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(60))
            Glide.with(context)
                .load(sliderItem.image)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        this.notifyDataSetChanged()
    }
}