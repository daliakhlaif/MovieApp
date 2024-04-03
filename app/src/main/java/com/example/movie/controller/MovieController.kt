package com.example.movie.controller
import com.example.movie.model.Movie
import com.example.movie.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieController {

    private val moviesList: ArrayList<Movie> = ArrayList()

    fun setMoviesList(movies: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(movies)
    }


    fun getAllMovies(): ArrayList<Movie> {
        return moviesList
    }

    fun fetchMovies(callback: (ArrayList<Movie>?, Throwable?) -> Unit) {
        val moviesApi = RetrofitClient.createMoviesApi()

        moviesApi.getShows().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        setMoviesList(it)
                        callback(getAllMovies(), null)
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

        fun getDurationString(duration: Int): String {
            val hr = duration / 60
            val min = duration % 60
            return "$hr h $min min"
        }

        fun removeHtmlTags(description: String): String {
            return android.text.Html.fromHtml(description, android.text.Html.FROM_HTML_MODE_LEGACY).toString()
        }

    }




