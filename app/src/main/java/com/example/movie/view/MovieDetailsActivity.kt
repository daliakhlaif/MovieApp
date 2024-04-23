package com.example.movie.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.databinding.ActivityMovieDetailsBinding
import com.example.movie.model.Movie
import com.example.movie.util.GlobalKeys
import com.example.movie.util.getParcelableExtraCompat
import com.example.movie.viewModel.MovieDetailsViewModel


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        val movie = intent.getParcelableExtraCompat<Movie>(GlobalKeys.ARG_MOVIE, Movie::class.java)
        viewModel.init(movie)
        observeMovieDetails()
    }

    private fun observeMovieDetails() {
        viewModel.movie.observe(this) { movie ->
            movie?.let {
                val movieDetailsFragment = MovieDetailsFragment.newInstance(it)
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, movieDetailsFragment)
                    .commit()
            }
        }
    }
}