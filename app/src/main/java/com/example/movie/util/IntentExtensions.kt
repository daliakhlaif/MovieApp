package com.example.movie.util

import android.content.Intent
import android.os.Build
import android.os.Parcelable

fun <T : Parcelable> Intent.getParcelableExtraCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, clazz)
    } else {
        getParcelableExtra(key)
    }
}