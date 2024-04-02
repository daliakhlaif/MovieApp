package com.example.movie.network

import com.example.movie.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MoviesAPI {
    @GET("shows")
    fun getShows(): Call<List<Movie>>
}