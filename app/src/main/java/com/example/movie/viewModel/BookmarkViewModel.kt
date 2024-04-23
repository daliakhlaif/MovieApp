package com.example.movie.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Movie
import com.example.movie.util.GlobalKeys
import com.example.movie.util.getMovieById

class BookmarkViewModel(private val context: Context) : ViewModel() {

    private val _bookmarkedMovies = MutableLiveData<List<Movie>>()
    val bookmarkedMovies: LiveData<List<Movie>> get() = _bookmarkedMovies

    init {
        fetchBookmarkedMovies()
    }

     fun fetchBookmarkedMovies() {
        val bookmarkedMovieIds = getBookmarkedMovieIds()
        val bookmarkedMovies = mutableListOf<Movie>()

        bookmarkedMovieIds?.forEach { movieId ->
            val movie = getMovieById(movieId.toInt())
            movie?.let {
                bookmarkedMovies.add(it)
            }
        }

        _bookmarkedMovies.value = bookmarkedMovies
    }

    private fun getBookmarkedMovieIds(): Set<String>? {
        val sharedPreferences = context.getSharedPreferences(GlobalKeys.BOOKMARK_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(GlobalKeys.BOOKMARKED_MOVIES, emptySet())
    }
}