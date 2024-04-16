package com.example.movie.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movie.model.Movie

class MovieDetailsViewModel : ViewModel() {
    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> get() = _movie

    private lateinit var sharedPreferences: SharedPreferences

    fun isBookmarked(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences("BookmarkPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("bookmarkedMovies", emptySet())?.contains(_movie.value?.movieId.toString()) ?: false
    }

    fun toggleBookmark(context: Context) {
        val isBookmarked = isBookmarked(context)
        val newBookmarkState = !isBookmarked
        saveBookmarkState(context, newBookmarkState)
        sendBookmarkUpdateBroadcast(context)
    }

    private fun saveBookmarkState(context: Context, bookmarkState: Boolean) {
        sharedPreferences = context.getSharedPreferences("BookmarkPrefs", Context.MODE_PRIVATE)
        val bookmarkedMovies = sharedPreferences.getStringSet("bookmarkedMovies", mutableSetOf())?.toMutableSet()
        if (bookmarkState) {
            bookmarkedMovies?.add(_movie.value?.movieId.toString())
        } else {
            bookmarkedMovies?.remove(_movie.value?.movieId.toString())
        }
        sharedPreferences.edit().putStringSet("bookmarkedMovies", bookmarkedMovies).apply()
    }

    private fun sendBookmarkUpdateBroadcast(context: Context) {
        val intent = Intent("com.example.movie.BOOKMARK_UPDATED")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    fun init(movie: Movie?) {
        _movie.value = movie
    }
}