package com.example.movie.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.databinding.ActivityMovieDetailsBinding
import com.example.movie.model.Movie


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        initialize()
    }

    private fun initialize(){
        setContentView(binding.root)

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           intent?.getParcelableExtra("movie", Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>("movie")
        }

        if (movie != null){     //launch movie details fragment
            val movieDetailsFragment = MovieDetailsFragment.newInstance(movie)
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, movieDetailsFragment)
                .commit()
        }
    }


}