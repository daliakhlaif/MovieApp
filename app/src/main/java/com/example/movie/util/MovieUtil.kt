package com.example.movie.util

import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository

fun getMovieById(movieId: Int): Movie? {
    val moviesList = MovieRepository._movies.value
    return moviesList?.find { it.movieId == movieId }
}

fun getDurationString(duration: Int): String {
    val hours = duration / 60
    val minutes = duration % 60
    return "$hours hr $minutes min"
}

fun removeHtmlTags(description: String): String {
    return android.text.Html.fromHtml(description, android.text.Html.FROM_HTML_MODE_LEGACY).toString()
}