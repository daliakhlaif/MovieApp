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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.databinding.FragmentMovieDetailsBinding
import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository
import com.example.movie.util.getDurationString
import com.example.movie.util.getParcelableExtraCompat
import com.example.movie.util.removeHtmlTags
import com.example.movie.viewModel.MovieDetailsViewModel


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(MovieDetailsViewModel::class.java)
        arguments?.let { bundle ->
            val movie = activity?.intent?.getParcelableExtraCompat("movie", Movie::class.java)
            viewModel.init(movie)
        }
        viewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                displayMovieDetails(it)
                updateBookmarkAppearance(viewModel.isBookmarked(context))
                binding.bookmark.setOnClickListener {
                    viewModel.toggleBookmark(context)
                    updateBookmarkAppearance(viewModel.isBookmarked(context))
                }
            }
        })
    }

    private fun updateBookmarkAppearance(isBookmarked: Boolean) {
        val drawableRes = if (isBookmarked) R.drawable.bookmark_pressed else R.drawable.bookmark_default
        binding.bookmark.setImageResource(drawableRes)
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.apply {
            movieName.text = movie.name
            rating.text = getString(R.string.outOfTen, movie.rating.average.toString())
            language.text = movie.language
            length.text = getDurationString(movie.duration)
            description.text = removeHtmlTags(movie.description)
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