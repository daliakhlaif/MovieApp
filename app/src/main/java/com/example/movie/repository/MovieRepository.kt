package com.example.movie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.model.Movie
import com.example.movie.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepository {

    val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun fetchMovies(callback: (List<Movie>?, Throwable?) -> Unit) {
        val moviesApi = RetrofitClient.createMoviesApi()

        moviesApi.getShows().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    response.body()?.let { movies ->
                        _movies.postValue(movies)
                        callback(movies, null)
                    } ?: callback(null, NullPointerException("Response body is null"))
                } else {
                    callback(null, Exception("Failed to retrieve movies: ${response.errorBody()}"))
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                callback(null, t)
            }
        })
    }


}