package com.example.myapplication.moviesapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.moviesapp.R
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.moviesapp.Adapters.FilmListAdapter
import com.example.myapplication.moviesapp.Adapters.SliderAdapter
import com.example.myapplication.moviesapp.Domain.ListFilm
import com.example.myapplication.moviesapp.Domain.SliderItem
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private val slideHandler = Handler()
    private lateinit var adapterBestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterUpComming: RecyclerView.Adapter<*>
    private lateinit var adapterNewMovies: RecyclerView.Adapter<*>
    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewUpComming: RecyclerView
    private lateinit var recyclerViewNewMovies: RecyclerView
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        banners()
        sendRequestNewMovies()
        sendRequestBestMovie()
        sendRequestUpComming()
    }

    // code for request data from API (https://moviesapi.ir/api/v1/movies?page={page})
    //<-------------------------------------------------------------------------------------------------->
    private fun sendRequestNewMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE
        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3",
        { response ->
            val gson = Gson()
            loading1.visibility = View.GONE
            val items = gson.fromJson(response, ListFilm::class.java)
            adapterNewMovies = FilmListAdapter(items)
            recyclerViewNewMovies.adapter = adapterNewMovies
        },
        { error ->
            loading1.visibility = View.GONE
            Log.i("MovieApp", "onErrorResponse: ${error.toString()}")
        })
        mRequestQueue.add(mStringRequest)
    }

    private fun sendRequestBestMovie() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading3.visibility = View.VISIBLE
        mStringRequest3 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2",
            { response ->
                val gson = Gson()
                loading3.visibility = View.GONE
                val items = gson.fromJson(response, ListFilm::class.java)
                adapterUpComming = FilmListAdapter(items)
                recyclerViewUpComming.adapter = adapterUpComming
            },
            { error ->
                loading3.visibility = View.GONE
                Log.i("MovieApp", "onErrorResponse: ${error.toString()}")
            })
        mRequestQueue.add(mStringRequest3)
    }

    private fun sendRequestUpComming() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE
        mStringRequest2 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
            { response ->
            val gson = Gson()
            loading2.visibility = View.GONE
            val items = gson.fromJson(response, ListFilm::class.java)
            adapterBestMovies = FilmListAdapter(items)
            recyclerViewBestMovies.adapter = adapterBestMovies
        },
        { error ->
            loading2.visibility = View.GONE
            Log.i("MovieApp", "onErrorResponse: " + error.toString())
        })
        mRequestQueue.add(mStringRequest2)
    }
    //<-------------------------------------------------------------------------------------------------->


    // code for make a slider banner
    //<-------------------------------------------------------------------------------------------------->
    private fun banners() {
        val sliderItems = mutableListOf<SliderItem>()
        sliderItems.add(SliderItem(R.drawable.wide))
        sliderItems.add(SliderItem(R.drawable.wide1))
        sliderItems.add(SliderItem(R.drawable.wide3))


        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.setCurrentItem(1)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                slideHandler.removeCallbacks(sliderRunnable)
            }
        })
    }


    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        slideHandler.postDelayed(sliderRunnable, 2000)
    }
    //<-------------------------------------------------------------------------------------------------->

    private fun initViews() {
        viewPager2 = findViewById(R.id.viewpagerSlider)
        recyclerViewNewMovies = findViewById(R.id.view1)
        recyclerViewNewMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBestMovies = findViewById(R.id.view2)
        recyclerViewBestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUpComming = findViewById(R.id.view3)
        recyclerViewUpComming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loading1 = findViewById(R.id.loading1)
        loading2 = findViewById(R.id.loading2)
        loading3 = findViewById(R.id.loading3)
    }
}