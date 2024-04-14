package com.example.movie.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.controller.MovieController
import com.example.movie.databinding.FragmentMovieDetailsBinding
import com.example.movie.model.Movie
import com.example.movie.util.getParcelableExtraCompat


class MovieDetailsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentMovieDetailsBinding? = null
    private lateinit var movie: Movie
    private val binding get() = _binding!!
    private val intent = Intent(BookmarkFragment.ACTION_BOOKMARK_UPDATED)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    private fun initialize() {
        sharedPreferences = requireContext().getSharedPreferences("BookmarkPrefs", Context.MODE_PRIVATE)

        binding.bookmark.setOnClickListener {
            toggleBookmark()
        }

        arguments?.let {
            movie = getMovieFromIntent() ?: return
            displayMovieDetails(movie)
            updateBookmarkAppearance(isBookmarked())
        }
    }

    private fun isBookmarked(): Boolean {
        val bookmarkedMovies = sharedPreferences.getStringSet("bookmarkedMovies", emptySet())
        return bookmarkedMovies?.contains(movie.movieId.toString()) ?: false
    }

    private fun toggleBookmark() {
        val isBookmarked = isBookmarked()
        val newBookmarkState = !isBookmarked
        saveBookmarkState(newBookmarkState)
        updateBookmarkAppearance(newBookmarkState)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

    private fun saveBookmarkState(bookmarkState: Boolean) {
        val bookmarkedMovies = sharedPreferences.getStringSet("bookmarkedMovies", mutableSetOf())?.toMutableSet()
        if (bookmarkState) {
            bookmarkedMovies?.add(movie.movieId.toString())
        } else {
            bookmarkedMovies?.remove(movie.movieId.toString())
        }
        sharedPreferences.edit().putStringSet("bookmarkedMovies", bookmarkedMovies).apply()
    }

    private fun updateBookmarkAppearance(isBookmarked: Boolean) {
        val drawableRes = if (isBookmarked) R.drawable.bookmark_pressed else R.drawable.bookmark_default
        binding.bookmark.setImageResource(drawableRes)
    }


    private fun getMovieFromIntent(): Movie? {
       return activity?.intent?.getParcelableExtraCompat("movie", Movie::class.java)
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.apply {
            movieName.text = movie.name
            rating.text = getString(R.string.outOfTen, movie.rating.average.toString())
            language.text = movie.language
            length.text = MovieController.getDurationString(movie.duration)
            description.text = MovieController.removeHtmlTags(movie.description)
            description.movementMethod = ScrollingMovementMethod()
            Glide.with(requireContext())
                .load(movie.poster.original)
                .into(imageView2)

            imageButton.setOnClickListener {
                activity?.finish()
            }

            linearForType.removeAllViews()
            movie.type.forEach { type ->
                val typeView = layoutInflater.inflate(R.layout.type_view, linearForType, false) as TextView
                typeView.text = type
                linearForType.addView(typeView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
        }
    }
}
