package com.example.movie.viewModel


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository
import com.example.movie.view.BookmarkFragment
import com.example.movie.view.HomeFragment

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies
    private val _selectedFragment = MutableLiveData<Fragment>()
    val selectedFragment: LiveData<Fragment> get() = _selectedFragment

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        MovieRepository.fetchMovies { movies, error ->
            if (error != null) {
                Log.e("CHECK_RESPONSE", "Failed to retrieve movies: ${error.message}")
            } else {
                movies?.let {
                    Log.i("CHECK_RESPONSE", "Movies retrieved successfully: $it")
                    _movies.postValue(it)
                }
            }
        }
    }

    fun onBookmarkSelected() {
        _selectedFragment.value = BookmarkFragment()
    }

    fun onHomeSelected() {
        _selectedFragment.value = HomeFragment()
    }
}