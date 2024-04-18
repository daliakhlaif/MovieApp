package com.example.movie.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movie.repository.MovieDatabaseHelper
import com.example.movie.model.Movie

class MovieDetailsViewModel : ViewModel() {
    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> get() = _movie
    private lateinit var movieDatabaseHelper: MovieDatabaseHelper

    fun isBookmarked(context: Context): Boolean {
        movieDatabaseHelper = MovieDatabaseHelper(context)
        return movieDatabaseHelper.getMovieIds().contains(_movie.value?.movieId)
    }

    fun toggleBookmark(context: Context) {
        val isBookmarked = isBookmarked(context)
        val newBookmarkState = !isBookmarked
        saveBookmarkState(context, newBookmarkState)
        sendBookmarkUpdateBroadcast(context)
    }

    private fun saveBookmarkState(context: Context, bookmarkState: Boolean) {
        if (bookmarkState) {
            _movie.value?.let { movieDatabaseHelper.addMovie(it.movieId) }
        } else {
            _movie.value?.let { movieDatabaseHelper.deleteMovie(it.movieId) }
        }
    }

    private fun sendBookmarkUpdateBroadcast(context: Context) {
        val intent = Intent("com.example.movie.BOOKMARK_UPDATED")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    fun init(movie: Movie?) {
        _movie.value = movie
    }
}