package com.example.movie.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MovieDatabase"
        private const val TABLE_NAME = "Movies"
        private const val COLUMN_ID = "MovieID"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addMovie(movieId: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, movieId)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun deleteMovie(movieId: Int): Boolean {
        val db = this.writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(movieId.toString())
        val deletedRows = db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
        return deletedRows > 0
    }

    fun getMovieIds(): List<Int> {
        val movieIds = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        cursor.use {
            while (it.moveToNext()) {
                val columnIndex = it.getColumnIndex(COLUMN_ID)
                if (columnIndex != -1) {
                    val movieId = it.getInt(columnIndex)
                    movieIds.add(movieId)
                }
            }
        }
        db.close()
        return movieIds
    }
}