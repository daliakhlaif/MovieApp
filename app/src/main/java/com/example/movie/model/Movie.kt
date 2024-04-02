package com.example.movie.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("name") val name: String,
    @SerializedName("genres") val type: List<String>,
    @SerializedName("rating") val rating: Double,
    @SerializedName("image") val poster: Int,
    @SerializedName("runtime") val duration: Int,
    @SerializedName("language") val language: String,
    @SerializedName("summary") val description: String
) : Parcelable