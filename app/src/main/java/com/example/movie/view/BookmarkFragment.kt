package com.example.movie.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.BookmarkedListAdapter
import com.example.movie.databinding.FragmentBookmarkBinding
import com.example.movie.model.Movie
import com.example.movie.util.GlobalKeys
import com.example.movie.viewModel.BookmarkViewModel
import com.example.movie.viewModel.BookmarkViewModelFactory


class BookmarkFragment : Fragment(), OnMovieItemClickListener {
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bookmarkedListAdapter: BookmarkedListAdapter
    private val viewModel: BookmarkViewModel by viewModels { BookmarkViewModelFactory(requireContext()) }

    companion object {
        const val ACTION_BOOKMARK_UPDATED = "com.example.movie.BOOKMARK_UPDATED"
    }

    private val bookmarkUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_BOOKMARK_UPDATED) {

                viewModel.fetchBookmarkedMovies()
                Log.i("check broadcast", "from receiver")
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
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        bookmarkedListAdapter = BookmarkedListAdapter(requireContext(), ArrayList(), this)
        binding.recycler.adapter = bookmarkedListAdapter
        observeBookmarkedMovies()
        registerBroadcastReceiver()
    }

    private fun observeBookmarkedMovies() {
        viewModel.bookmarkedMovies.observe(viewLifecycleOwner) { movies ->
            bookmarkedListAdapter.updateMoviesList(movies as ArrayList<Movie>)
        }
    }

    private fun registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(bookmarkUpdateReceiver, IntentFilter(ACTION_BOOKMARK_UPDATED))
    }

    private fun unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(bookmarkUpdateReceiver)
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(GlobalKeys.ARG_MOVIE, movie)
        }
        startActivity(intent)
    }


}