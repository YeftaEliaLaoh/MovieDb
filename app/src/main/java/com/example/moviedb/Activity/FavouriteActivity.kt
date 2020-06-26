package com.example.moviedb.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviedb.Adapter.FavouriteAdapter
import com.example.moviedb.Database.FavouriteDatabase
import com.example.moviedb.Interface.Popinterface
import com.example.moviedb.Model.MovieSearch
import com.example.moviedb.R
import com.example.moviedb.Util.Config.Companion.API_KEY
import kotlinx.android.synthetic.main.activity_favorite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavouriteActivity : AppCompatActivity() {

    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(Popinterface::class.java)
    var favList: ArrayList<MovieSearch> = arrayListOf()
    var check: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        favList.clear()

        val db: FavouriteDatabase by lazy {
            Room.databaseBuilder(
                this,
                FavouriteDatabase::class.java,
                "Fav.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        var a = db.FavDao().getallfav()

        for (i in 0..a.lastIndex) {

            service.getmovies(a[i].movie_id.toInt(), API_KEY)
                .enqueue(object : Callback<MovieSearch> {
                    override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<MovieSearch>,
                        response: Response<MovieSearch>
                    ) {

                        val data = response.body()
                        Log.d("gggg", data!!.original_title)
                        favList.add(data!!)

                        if (i == a.lastIndex) {
                            if (favList.size != 0) {
                                check = false
                            }

                            rvfav.layoutManager =
                                GridLayoutManager(
                                    this@FavouriteActivity,
                                    2,
                                    RecyclerView.VERTICAL,
                                    false
                                )
                            rvfav.adapter =
                                FavouriteAdapter(
                                    this@FavouriteActivity,
                                    favList,
                                    check
                                )
                            rvfav.adapter!!.notifyDataSetChanged()
                        }


                    }
                })
        }
        favList.clear()

    }

    override fun onBackPressed() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity()
        }
    }

}
