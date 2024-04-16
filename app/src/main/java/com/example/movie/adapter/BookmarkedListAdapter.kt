package com.example.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.databinding.MovieListItemBinding
import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository
import com.example.movie.util.getDurationString
import com.example.movie.view.OnMovieItemClickListener

class BookmarkedListAdapter(
    private val context: Context,
    private var moviesList: ArrayList<Movie>,
    private val onMovieItemClickListener: OnMovieItemClickListener
) : RecyclerView.Adapter<BookmarkedListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position], onMovieItemClickListener)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateMoviesList(newList: ArrayList<Movie>) {
        moviesList.clear()
        moviesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: OnMovieItemClickListener) {
            binding.movieTitle.text = movie.name
            binding.ratingView.text = context.getString(R.string.outOfTen, movie.rating.average.toString())
            binding.durationView.text = getDurationString(movie.duration)

            Glide.with(context)
                .load(movie.poster.original)
                .into(binding.imageView)

            val inflater = LayoutInflater.from(context)
            val linearLayout = binding.linearTypeViews

            linearLayout.removeAllViews()
            for (type in movie.type) {
                val typeView = inflater.inflate(R.layout.type_view, linearLayout, false) as TextView
                typeView.text = type
                linearLayout.addView(typeView)
            }

            itemView.setOnClickListener {
                listener.onItemClick(movie)
            }
        }
    }
}