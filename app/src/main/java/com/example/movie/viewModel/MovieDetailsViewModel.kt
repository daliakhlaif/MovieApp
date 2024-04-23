package com.example.movie.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movie.model.Movie
import com.example.movie.util.GlobalKeys
import com.example.movie.view.BookmarkFragment

class MovieDetailsViewModel : ViewModel() {
    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> get() = _movie

    private lateinit var sharedPreferences: SharedPreferences

    fun isBookmarked(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences(GlobalKeys.BOOKMARK_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(GlobalKeys.BOOKMARKED_MOVIES, emptySet())?.contains(_movie.value?.movieId.toString()) ?: false
    }

    fun toggleBookmark(context: Context) {
        val isBookmarked = isBookmarked(context)
        val newBookmarkState = !isBookmarked
        saveBookmarkState(context, newBookmarkState)
        sendBookmarkUpdateBroadcast(context)
    }

    private fun saveBookmarkState(context: Context, bookmarkState: Boolean) {
        sharedPreferences = context.getSharedPreferences(GlobalKeys.BOOKMARK_PREFS_NAME, Context.MODE_PRIVATE)
        val bookmarkedMovies = sharedPreferences.getStringSet(GlobalKeys.BOOKMARKED_MOVIES, mutableSetOf())?.toMutableSet()
        if (bookmarkState) {
            bookmarkedMovies?.add(_movie.value?.movieId.toString())
        } else {
            bookmarkedMovies?.remove(_movie.value?.movieId.toString())
        }
        sharedPreferences.edit().putStringSet(GlobalKeys.BOOKMARKED_MOVIES, bookmarkedMovies).apply()
    }

    private fun sendBookmarkUpdateBroadcast(context: Context) {
        val intent = Intent(BookmarkFragment.ACTION_BOOKMARK_UPDATED)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    fun init(movie: Movie?) {
        _movie.value = movie
    }
}