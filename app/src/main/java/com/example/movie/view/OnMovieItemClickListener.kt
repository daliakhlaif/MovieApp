package com.example.movie.view

import com.example.movie.model.Movie

interface OnMovieItemClickListener {
    fun onItemClick(movie: Movie)
}