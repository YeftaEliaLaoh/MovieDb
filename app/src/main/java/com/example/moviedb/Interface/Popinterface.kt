package com.example.moviedb.Interface

import com.example.moviedb.Model.MovieResponse
import com.example.moviedb.Model.MovieSearch
import com.example.moviedb.Model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Popinterface {

    @GET("3/movie/popular")
    fun getPopular(
        @Query("api_key") key: String,
        @Query("page") page: String
    ): Call<MovieResponse>

    @GET("3/movie/top_rated")
    fun getToprated(

        @Query("api_key") key: String,
        @Query("page") page: String
    ): Call<MovieResponse>

    @GET("3/movie/upcoming")
    fun getUpcoming(

        @Query("api_key") key: String,
        @Query("page") page: String
    ): Call<MovieResponse>

    @GET("3/movie/now_playing")
    fun getNowplaying(

        @Query("api_key") key: String,
        @Query("page") page: String
    ): Call<MovieResponse>

    @GET("3/search/movie")
    fun getSearchMovie(
        @Query("api_key") key : String,
        @Query("query") query : String
    ) : Call<SearchResponse>


    @GET("3/movie/{id}")
    fun getmovies(

        @Path("id") id: Int,
        @Query("api_key") key: String
    ): Call<MovieSearch>
}
