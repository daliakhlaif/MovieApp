package com.example.movie.controller

import com.example.movie.model.Image
import com.example.movie.model.Movie
import com.example.movie.model.Rating

class MovieController {

    private val moviesList: ArrayList<Movie> = ArrayList()


    fun addMovie(name: String, type: List<String>, rating: Rating, poster: Image, duration: Int, language: String, description: String) {
        val movie = Movie(name, type, rating, poster, duration, language, description)
        moviesList.add(movie)
    }


    fun getAllMovies(): ArrayList<Movie> {
        return moviesList
    }

    fun getMovieByName(name: String): Movie? {
        return moviesList.find { it.name == name }
    }

    companion object {
        fun getDurationString(duration: Int): String {
            val hr = duration / 60
            val min = duration % 60
            return "$hr h $min min"
        }
    }
    fun getMoviesByType(type: String): List<Movie> {
        return moviesList.filter { it.type.contains(type) }
    }


    fun removeMovie(movie: Movie) {
        moviesList.remove(movie)
    }


}
