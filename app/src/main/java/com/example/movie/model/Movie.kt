package com.example.movie.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") val movieId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("genres") val type: List<String>,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("image") val poster: Image,
    @SerializedName("runtime") val duration: Int,
    @SerializedName("language") val language: String,
    @SerializedName("summary") val description: String
) : Parcelable