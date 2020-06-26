package com.example.moviedb.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import androidx.room.Room

import com.example.moviedb.Database.Favourite
import com.example.moviedb.Database.FavouriteDatabase
import com.example.moviedb.Interface.Popinterface

import com.example.moviedb.Model.MovieSearch
import com.example.moviedb.R
import com.example.moviedb.Util.Config.Companion.API_KEY
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"
    val service = retrofit.create(Popinterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id").toInt()

        val db: FavouriteDatabase by lazy {
            Room.databaseBuilder(
                this,
                FavouriteDatabase::class.java,
                "Fav.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        var fill = 0

        val imgfav = findViewById<ImageView>(R.id.imagefav)

        val isFav = db.FavDao().isFavourite(id.toString())

        if (isFav == null) {
            fill = 0
            imgfav.setImageResource(R.drawable.ic_favorite_border)
        }
        else {
            fill = 1
            imgfav.setImageResource(R.drawable.ic_favorite_fill)
        }

        imgfav.setOnClickListener {

            if (fill == 1) {
                imgfav.setImageResource(R.drawable.ic_favorite_border)
                db.FavDao().delete(id.toString())
                Toast.makeText(this, "Removed from favourite", Toast.LENGTH_SHORT).show()
                fill = 0

            }
            else {

                service.getmovies(id, API_KEY).enqueue(object : Callback<MovieSearch> {
                    override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                        Log.d("MoviesDagger", t.toString())
                    }

                    override fun onResponse(
                        call: Call<MovieSearch>,
                        response: Response<MovieSearch>
                    ) {

                        val data = response.body()
                        if (response.isSuccessful) {
                            var obj = Favourite(
                                movie_id = id.toString(),
                                path = data!!.poster_path.toString()
                            )
                            db.FavDao().insertRow(obj)
                            imgfav.setImageResource(R.drawable.ic_favorite_fill)
                            Toast.makeText(
                                this@DetailActivity,
                                "Added to favourite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                })

            }
        }

        service.getmovies(id, API_KEY).enqueue(object : Callback<MovieSearch> {
            override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }

            override fun onResponse(call: Call<MovieSearch>, response: Response<MovieSearch>) {

                val data = response.body()

                if (data != null) {
                    Picasso.get().load(baseURL + data.poster_path).into(imageview2)
                }
                tv7.text = data?.original_title
                tvoverview.text = data?.overview
                tv1.text = "Release Date    " + data?.release_date

                val toolbar: Toolbar = findViewById(R.id.toolbar)
                toolbar.setTitle(data?.original_title)
                setSupportActionBar(toolbar)
                getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            }
        })


    }

    override fun onBackPressed() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity()
        }
    }

}
