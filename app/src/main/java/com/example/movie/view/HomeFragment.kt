package com.example.movie.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.controller.MovieController
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.model.Movie

class HomeFragment : Fragment(), OnMovieItemClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var movieController: MovieController


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
        movieController = MovieController()
        addMovies()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(context)
        movieListAdapter = MovieListAdapter(requireContext() ,movieController.getAllMovies())
        binding.recycler.adapter = movieListAdapter

        movieListAdapter.setOnMovieItemClickListener(this)
    }

    override fun onItemClick(movie: Movie){
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    private fun addMovies() {
        movieController.addMovie(
            resources.getString(R.string.movie1),
            listOf("Action"),
            6.4,
            R.drawable.venom_poster,
            90,
            "English",
            resources.getString(R.string.movie1desc)
        )
        movieController.addMovie(
            resources.getString(R.string.movie2),
            listOf("Drama", "Fantasy"),
            9.1,
            R.drawable.spider_man,
            148,
            "English",
            resources.getString(R.string.movie2desc)
        )
        movieController.addMovie(
            resources.getString(R.string.movie3),
            listOf("Comedy", "Drama"),
            6.7,
            R.drawable.jumanji,
            123,
            "English",
            resources.getString(R.string.movie3desc)
        )

        movieController.addMovie(
            resources.getString(R.string.movie1),
            listOf("Action"),
            6.4,
            R.drawable.venom_poster,
            90,
            "English",
            resources.getString(R.string.movie1desc)
        )

        movieController.addMovie(
            resources.getString(R.string.movie2),
            listOf("Drama", "Fantasy"),
            9.1,
            R.drawable.spider_man,
            148,
            "English",
            resources.getString(R.string.movie2desc)
        )

        movieController.addMovie(
            resources.getString(R.string.movie3),
            listOf("Comedy", "Drama"),
            6.7,
            R.drawable.jumanji,
            123,
            "English",
            resources.getString(R.string.movie3desc)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

