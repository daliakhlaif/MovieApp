package com.example.movie.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.databinding.ActivityMovieDetailsBinding
import com.example.movie.model.Movie
import com.example.movie.util.getParcelableExtraCompat


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize(){

        val movie =  intent.getParcelableExtraCompat<Movie>("movie")
        movie?.let {
            val movieDetailsFragment = MovieDetailsFragment.newInstance(it)
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, movieDetailsFragment)
                .commit()
        }
    }


}