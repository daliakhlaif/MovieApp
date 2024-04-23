package com.example.movie.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository

class HomeViewModel : ViewModel() {
    private val tag = "CHECK_RESPONSE"
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        MovieRepository.fetchMovies { movies, error ->
            if (error != null) {
                Log.e(tag, "Failed to retrieve movies: ${error.message}")
            } else {
                movies?.let {
                    Log.i(tag, "Movies retrieved successfully: $it")
                    _movies.postValue(it)
                }
            }
        }
    }
}
