package com.example.myapplication.moviesapp.Activities

import ActorListAdapter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.myapplication.moviesapp.Adapters.CategoryMovieList
import com.example.myapplication.moviesapp.Domain.MovieItems
import com.example.myapplication.moviesapp.R
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTxt: TextView
    private lateinit var movieRateTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummary: TextView
    private lateinit var movieActors: TextView
    private var idMovie: Int = 0
    private lateinit var picDetail: ImageView
    private lateinit var back: ImageView
    private lateinit var adapterActorList: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        idMovie = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    //request data from API(https://moviesapi.ir/api/v1/movies/{movie_id}) for detail movie
    private fun sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/$idMovie",
            { response ->
                val gson = Gson()
                progressBar.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
                val items = gson.fromJson(response, MovieItems::class.java)
                Glide.with(this)
                    .load(items.poster)
                    .into(picDetail)
                titleTxt.text = items.title
                movieRateTxt.text = items.imdbRating
                movieTimeTxt.text = items.runtime
                movieSummary.text = items.plot
                movieActors.text = items.actors
                items.images?.let {
                    adapterActorList = ActorListAdapter(it)
                    recyclerViewActors.adapter = adapterActorList
                }
                items.genres?.let {
                    adapterCategory = CategoryMovieList(it)
                    recyclerViewCategory.adapter = adapterCategory
                }
            },
            { error -> progressBar.visibility = View.GONE })
        mRequestQueue.add(mStringRequest)
    }

    private fun initView() {
        titleTxt = findViewById(R.id.moviesNameTxt)
        progressBar = findViewById(R.id.detailLoading)
        scrollView = findViewById(R.id.scrollView3)
        picDetail = findViewById(R.id.picDetail)
        movieRateTxt = findViewById(R.id.movieRate)
        movieTimeTxt = findViewById(R.id.movieTime)
        movieSummary = findViewById(R.id.movieSummary)
        movieActors = findViewById(R.id.movieActors)
        back = findViewById(R.id.back)
        recyclerViewActors = findViewById(R.id.actorsImg)
        recyclerViewCategory = findViewById(R.id.genreView)
        recyclerViewActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        back.setOnClickListener { finish() }
    }
}