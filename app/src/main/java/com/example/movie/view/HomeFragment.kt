package com.example.movie.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.controller.MovieController
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.model.Movie

class HomeFragment : Fragment(), OnMovieItemClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieListAdapter: MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize(){
        fetchMovies()
    }



    override fun onItemClick(movie: Movie){
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }


    private fun fetchMovies() {
        MovieController.fetchMovies { movies, error ->
            if (error != null) {
                Log.e("CHECK_RESPONSE", "Failed to retrieve movies: ${error.message}")
            } else {
                movies?.let {
                    Log.i("CHECK_RESPONSE", "Movies retrieved successfully: $it")
                    displayMovies(it)
                }
            }
        }
    }

    private fun displayMovies(movies: ArrayList<Movie>) {
        binding.recycler.layoutManager = LinearLayoutManager(context)
        movieListAdapter = MovieListAdapter(requireContext(), movies, this)
        binding.recycler.adapter = movieListAdapter
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

