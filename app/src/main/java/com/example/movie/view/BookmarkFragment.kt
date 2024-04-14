package com.example.movie.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.controller.MovieController
import com.example.movie.databinding.FragmentBookmarkBinding
import com.example.movie.model.Movie



class BookmarkFragment : Fragment(), OnMovieItemClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bookmarkedListAdapter: BookmarkedListAdapter
    private lateinit var binding: FragmentBookmarkBinding

    companion object {
        const val ACTION_BOOKMARK_UPDATED = "com.example.movie.BOOKMARK_UPDATED"
    }

    private val bookmarkUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_BOOKMARK_UPDATED) {
                displayBookmarkedMovies()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        sharedPreferences = requireContext().getSharedPreferences("BookmarkPrefs", Context.MODE_PRIVATE)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        bookmarkedListAdapter = BookmarkedListAdapter(requireContext(), getBookmarkedMovies(), this)
        binding.recycler.adapter = bookmarkedListAdapter
        displayBookmarkedMovies()
        registerBroadcastReceiver()

    }

    private fun registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(bookmarkUpdateReceiver, IntentFilter(ACTION_BOOKMARK_UPDATED))
    }

    private fun unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(bookmarkUpdateReceiver)
    }

    private fun getBookmarkedMovies(): ArrayList<Movie> {
        val bookmarkedMovies = ArrayList<Movie>()
        val bookmarkedMovieIds = sharedPreferences.getStringSet("bookmarkedMovies", emptySet())

        if (bookmarkedMovieIds != null) {
            for (movieId in bookmarkedMovieIds) {
                val movie = MovieController.getMovieById(movieId.toInt())
                movie?.let { bookmarkedMovies.add(it) }
            }
        }
        return bookmarkedMovies
    }

    private fun displayBookmarkedMovies() {
        bookmarkedListAdapter.updateMoviesList(getBookmarkedMovies())
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterBroadcastReceiver()
    }


}