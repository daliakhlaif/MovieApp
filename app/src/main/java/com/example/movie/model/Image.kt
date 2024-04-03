package com.example.movie.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("medium") val medium: String,
    @SerializedName("original") val original: String
) : Parcelable