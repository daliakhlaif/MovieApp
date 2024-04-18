package com.example.movie.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.MovieListAdapter
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.model.Movie
import com.example.movie.viewModel.HomeViewModel

class HomeFragment : Fragment(), OnMovieItemClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        initialize()
    }

    private fun initialize(){
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            displayMovies(movies as ArrayList<Movie>)
        }
    }



    override fun onItemClick(movie: Movie){
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
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