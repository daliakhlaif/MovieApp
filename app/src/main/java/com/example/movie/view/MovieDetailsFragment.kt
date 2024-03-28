package com.example.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.controller.MovieController
import com.example.movie.databinding.FragmentMovieDetailsBinding
import com.example.movie.model.Movie
import org.parceler.Parcels

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

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

    private fun initialize(){
        arguments?.let { args ->
            @Suppress("DEPRECATION")
            val movie: Movie? = Parcels.unwrap(args.getParcelable(ARG_MOVIE))
            movie?.let { displayMovieDetails(it) }
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.apply {
            movieName.text = movie.name
            rating.text = getString(R.string.outOfTen, movie.rating.toString())
            language.text = movie.language
            length.text = MovieController.getDurationString(movie.duration)
            description.text = movie.description
            imageView2.setImageResource(movie.posterResId)

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
                    putParcelable(ARG_MOVIE, Parcels.wrap(movie))
                }
            }
        }
    }
}
