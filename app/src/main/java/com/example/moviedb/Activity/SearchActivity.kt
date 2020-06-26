package com.example.moviedb.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.Adapter.SearchAdapter
import com.example.moviedb.Interface.Popinterface
import com.example.moviedb.Model.SearchResponse
import com.example.moviedb.R
import com.example.moviedb.Util.Config.Companion.API_KEY
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Popinterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val query = intent.getStringExtra("text")
        progressBarsearch.isVisible = true

        service.getSearchMovie(API_KEY, query).enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                val data = response.body()
                val data1 = data?.results
                progressBarsearch.isVisible = false

                rViewnew.layoutManager =
                    GridLayoutManager(this@SearchActivity, 2, RecyclerView.VERTICAL, false)
                rViewnew.adapter = data1?.let {
                    SearchAdapter(
                        this@SearchActivity,
                        it,
                        false
                    )
                }

            }
        })


    }
}
