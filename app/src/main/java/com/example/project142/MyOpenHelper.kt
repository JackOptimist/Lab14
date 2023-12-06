package com.example.project142

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyOpenHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_NAME_1 + " TEXT, " +
                FIELD_NAME_2 + " TEXT)"
        db.execSQL(query)
        Log.d("myLogs", "| Create |$db")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("myLogs", "| Upgrade |$db")
    }

    companion object {
        const val TABLE_NAME = "first_table"
        const val FIELD_NAME_1 = "first_field"
        const val FIELD_NAME_2 = "second_field"
    }
}