package com.example.movie.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val name: String,
    val type: List<String>,   // movie types
    val rating: Double,
    val posterResId: Int,  // movie poster id
    val duration: Int,
    val language: String, // New field for language
    val description: String // New field for description
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(type)
        parcel.writeDouble(rating)
        parcel.writeInt(posterResId)
        parcel.writeInt(duration)
        parcel.writeString(language)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
