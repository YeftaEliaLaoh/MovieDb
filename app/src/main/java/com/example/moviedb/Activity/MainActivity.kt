package com.example.moviedb.Activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.Adapter.DetailListAdapter
import com.example.moviedb.Controller.MainActivityController
import com.example.moviedb.Interface.Popinterface
import com.example.moviedb.Model.MovieResponse
import com.example.moviedb.R
import com.example.moviedb.Util.Config.Companion.API_KEY
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var imgBtnShowNavigation: ImageView? = null
    private lateinit var mainActivityController: MainActivityController
    private lateinit var layoutNavigation: LinearLayout
    private var buttonCategory: Button? = null

    private var btnMovie: ImageView? = null
    private var btnFavorite: ImageView? = null


    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Popinterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        initEvent()

        service.getPopular(API_KEY, "1").enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                val data = response.body()
                val data1 = data?.results

                progressBar.isVisible = false

                rView.layoutManager =
                    GridLayoutManager(this@MainActivity, 2)
                rView.adapter = data1?.let {
                    DetailListAdapter(
                        this@MainActivity,
                        it,
                        false
                    )
                }

            }
        })
    }

    fun initLayout() {


        mainActivityController = MainActivityController()
        imgBtnShowNavigation = this.findViewById(R.id.imgbtn_navigation)
        layoutNavigation = this.findViewById(R.id.layout_navigation)
        buttonCategory = this.findViewById(R.id.category_btn)

        btnMovie = this.findViewById(R.id.ic_page_movie)
        btnFavorite = this.findViewById(R.id.ic_page_favorite)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    fun initEvent() {

        imgBtnShowNavigation!!.setOnClickListener {
            mainActivityController.showOrHideNavigation(layoutNavigation)
        }

        btnMovie?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intent, null)
            }
        }

        btnFavorite?.setOnClickListener {
            val intent = Intent(this, FavouriteActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intent, null)
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        var manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchitem = menu?.findItem(R.id.searchid)
        var searchview = searchitem?.actionView as SearchView
        searchview.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchview.queryHint = "Search Movies Here..."
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("text", query)
                intent.putExtra("type", "movie")
                startActivity(intent)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchid -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}
