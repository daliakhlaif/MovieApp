package com.example.movie.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.repository.MovieDatabaseHelper
import com.example.movie.model.Movie
import com.example.movie.util.getMovieById

class BookmarkViewModel(private val context: Context) : ViewModel() {

    private val _bookmarkedMovies = MutableLiveData<List<Movie>>()
    val bookmarkedMovies: LiveData<List<Movie>> get() = _bookmarkedMovies
    private lateinit var movieDatabaseHelper: MovieDatabaseHelper

    init {
        fetchBookmarkedMovies()
    }

    fun fetchBookmarkedMovies() {
        val bookmarkedMovieIds = getBookmarkedMovieIds()
        val bookmarkedMovies = mutableListOf<Movie>()

        bookmarkedMovieIds.forEach { movieId ->
            val movie = getMovieById(movieId)
            movie?.let {
                bookmarkedMovies.add(it)
            }
        }

        _bookmarkedMovies.value = bookmarkedMovies
    }

    private fun getBookmarkedMovieIds(): List<Int> {
        movieDatabaseHelper = MovieDatabaseHelper(context)
        return movieDatabaseHelper.getMovieIds()
    }
}