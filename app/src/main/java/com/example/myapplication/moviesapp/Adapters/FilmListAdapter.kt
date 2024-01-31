package com.example.myapplication.moviesapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.moviesapp.Activities.DetailActivity
import com.example.myapplication.moviesapp.Domain.ListFilm
import com.example.myapplication.moviesapp.R
//class for make a film list
class FilmListAdapter(private val items: ListFilm) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_movie, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items.getData()[position].getTitle()
        holder.scoreTxt.text = items.getData()[position].getImdbRating().toString()
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
        Glide.with(context)
            .load(items.getData()[position].getPoster())
            .apply(requestOptions)
            .into(holder.pic)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", items.getData()[position].getId())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.getData().size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        var scoreTxt: TextView = itemView.findViewById(R.id.scoreTxt)
        var pic: ImageView = itemView.findViewById(R.id.pic)
    }
}


