package com.example.moviedb.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.Adapter.DetailListAdapter
import com.example.moviedb.Controller.MainActivityController
import com.example.moviedb.Interface.Popinterface
import com.example.moviedb.Model.Movie
import com.example.moviedb.Model.MovieResponse
import com.example.moviedb.R
import com.example.moviedb.Util.Config.Companion.API_KEY
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListByActivity : AppCompatActivity() {
    private var imgBtnShowNavigation: ImageView? = null
    private lateinit var mainActivityController: MainActivityController
    private lateinit var layoutNavigation: LinearLayout
    private var buttonCategory: Button? = null

    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Popinterface::class.java)

    var isScrolling: Boolean = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrolledOutItems: Int = 0
    var currentPage: Int = 1
    var i = 0
    lateinit var commonList: ArrayList<Movie>
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var gridLayoutManager: GridLayoutManager? = null

    var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        type = intent.getStringExtra("type")

        initLayout()
        initEvent()

        fun toBeCalled() {

            if (type == "Popular") {

                service.getPopular(API_KEY, currentPage.toString())
                    .enqueue(object : Callback<MovieResponse> {
                        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                            Log.d("MoviesDagger", t.toString())
                        }

                        override fun onResponse(
                            call: Call<MovieResponse>,
                            response: Response<MovieResponse>
                        ) {
                            val data = response.body()
                            val data1 = data!!.results

                            progressBar.isVisible = false

                            if (i == 0) {
                                commonList = data1
                                rView.layoutManager =
                                    GridLayoutManager(this@ListByActivity, 2)
                                rView.adapter = DetailListAdapter(
                                    this@ListByActivity,
                                    commonList,
                                    false
                                )
                            }
                            else {
                                commonList.addAll(data1)
                                rView.adapter!!.notifyDataSetChanged()

                            }
                            i++

                        }
                    })
            }
            else
                if (type == "Now Playing") {

                    service.getNowplaying(API_KEY, currentPage.toString())
                        .enqueue(object : Callback<MovieResponse> {
                            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                            }

                            override fun onResponse(
                                call: Call<MovieResponse>,
                                response: Response<MovieResponse>
                            ) {

                                val data = response.body()
                                val data1 = data!!.results
                                progressBar.isVisible = false

                                if (i == 0) {
                                    commonList = data1
                                    rView.layoutManager =
                                        GridLayoutManager(this@ListByActivity, 2)
                                    rView.adapter = DetailListAdapter(
                                        this@ListByActivity,
                                        commonList,
                                        false
                                    )
                                }
                                else {
                                    commonList.addAll(data1)
                                    rView.adapter!!.notifyDataSetChanged()
                                }
                                i++

                            }
                        })
                }
                else
                    if (type == "Top Rated") {

                        service.getToprated(API_KEY, currentPage.toString())
                            .enqueue(object : Callback<MovieResponse> {
                                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                                    Log.d("MoviesDagger", t.toString())
                                }

                                override fun onResponse(
                                    call: Call<MovieResponse>,
                                    response: Response<MovieResponse>
                                ) {

                                    val data = response.body()
                                    val data1 = data!!.results
                                    progressBar.isVisible = false

                                    if (i == 0) {
                                        commonList = data1
                                        rView.layoutManager =
                                            GridLayoutManager(this@ListByActivity, 2)
                                        rView.adapter = DetailListAdapter(
                                            this@ListByActivity,
                                            commonList,
                                            false
                                        )
                                    }
                                    else {
                                        commonList.addAll(data1)
                                        rView.adapter!!.notifyDataSetChanged()

                                    }
                                    i++

                                }
                            })
                    }
                    else
                        if (type == "Upcoming") {

                            service.getUpcoming(API_KEY, currentPage.toString())
                                .enqueue(object : Callback<MovieResponse> {
                                    override fun onFailure(
                                        call: Call<MovieResponse>,
                                        t: Throwable
                                    ) {
                                        Log.d("MoviesDagger", t.toString())
                                    }

                                    override fun onResponse(
                                        call: Call<MovieResponse>,
                                        response: Response<MovieResponse>
                                    ) {

                                        val data = response.body()
                                        val data1 = data!!.results
                                        progressBar.isVisible = false

                                        if (i == 0) {
                                            commonList = data1
                                            rView.layoutManager =
                                                GridLayoutManager(this@ListByActivity, 2)
                                            rView.adapter = DetailListAdapter(
                                                this@ListByActivity,
                                                commonList,
                                                false
                                            )
                                        }
                                        else {
                                            commonList.addAll(data1)
                                            rView.adapter!!.notifyDataSetChanged()
                                        }
                                        i++

                                    }
                                })
                        }

        }
        toBeCalled()

        rView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager = rView.layoutManager!!
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                when (layoutManager) {
                    is GridLayoutManager -> gridLayoutManager = layoutManager as GridLayoutManager
                }
                scrolledOutItems = gridLayoutManager!!.findFirstVisibleItemPosition()

                if ((scrolledOutItems + currentItems == totalItems) && isScrolling) {
                    currentPage++
                    isScrolling = false
                    toBeCalled()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })

    }

    fun initLayout() {
        mainActivityController = MainActivityController()
        imgBtnShowNavigation = this.findViewById(R.id.imgbtn_navigation)
        layoutNavigation = this.findViewById(R.id.layout_navigation)
        buttonCategory = this.findViewById(R.id.category_btn)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(type + " Movie")
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    fun initEvent() {

        imgBtnShowNavigation!!.setOnClickListener {
            mainActivityController.showOrHideNavigation(layoutNavigation)
        }

        buttonCategory?.setOnClickListener {
            val popup =
                PopupMenu(this, buttonCategory)
            popup.menuInflater.inflate(R.menu.category_dropdown, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val id = item.itemId
                when (id) {
                    R.id.popular -> {

                        val intent = Intent(this, ListByActivity::class.java)
                        intent.putExtra("type", "Popular")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent, null)
                        }

                    }
                    R.id.upcoming -> {

                        val intent = Intent(this, ListByActivity::class.java)
                        intent.putExtra("type", "Upcoming")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent, null)
                        }

                    }
                    R.id.top_rated -> {

                        val intent = Intent(this, ListByActivity::class.java)
                        intent.putExtra("type", "Top Rated")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent, null)
                        }

                    }
                    R.id.now_playing -> {

                        val intent = Intent(this, ListByActivity::class.java)
                        intent.putExtra("type", "Now Playing")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent, null)
                        }

                    }
                }
                true
            }
            popup.show()
        }
    }

}
