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
import com.example.movie.network.MoviesAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), OnMovieItemClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var movieController: MovieController
    private val moviesList = ArrayList<Movie>()

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
        fetchMovies()
        displayMovies(moviesList)
    }



    override fun onItemClick(movie: Movie){
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }




    private fun fetchMovies() {

        val tag = "CHECK_RESPONSE"
        val baseURL = "https://api.tvmaze.com/"

        val api = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesAPI::class.java)

        api.getShows().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (movie in it){
                            moviesList.add(movie)
                            Log.i(tag, "onResponse: $movie")
                        }
                    }
                }

            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.i(tag, "onFailure: ${t.message}")

            }


        })
    }

    private fun displayMovies(movies: ArrayList<Movie>) {
        binding.recycler.layoutManager = LinearLayoutManager(context)
        movieListAdapter = MovieListAdapter(requireContext(), movies)
        binding.recycler.adapter = movieListAdapter
        movieListAdapter.setOnMovieItemClickListener(this)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

