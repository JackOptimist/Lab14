package com.example.project142

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText


class MainActivity : Activity() {
    private var myHelper: MyOpenHelper? = null
    private var field1: EditText? = null
    private var field2: EditText? = null
    private var result: EditText? = null
    private var insertButton: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myHelper = MyOpenHelper(this, "myDB", null, 1)
        field1 = findViewById(R.id.field1)
        field2 = findViewById(R.id.field2)
        result = findViewById(R.id.dbResult)
        insertButton = findViewById(R.id.btnInsert)

        field1?.isEnabled = false
        field2?.isEnabled = false

        val readButton = findViewById<Button>(R.id.btnRead)
        val deleteButton = findViewById<Button>(R.id.btnDelete)

        insertButton?.setOnClickListener {
            enableInputFields()
        }
        readButton.setOnClickListener { readDatabase() }
        deleteButton.setOnClickListener { deleteDatabase() }
    }

    private fun enableInputFields() {
        field1?.isEnabled = true
        field2?.isEnabled = true
    }


    @SuppressLint("SetTextI18n")
    fun readDatabase() {

        if (field1!!.text.toString() != "" && field2!!.text.toString() != "") {
            Log.d(
                "myLogs",
                "Insert INTO DB (" + field1!!.text.toString() + ", " + field2!!.text.toString() + ")"
            )
            val DB = myHelper!!.writableDatabase
            val query = ((("CREATE TABLE IF NOT EXISTS " + MyOpenHelper.TABLE_NAME).toString() +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MyOpenHelper.FIELD_NAME_1).toString() + " TEXT, " +
                    MyOpenHelper.FIELD_NAME_2).toString() + " TEXT)"
            DB.execSQL(query)
            val CV = ContentValues()
            CV.put(MyOpenHelper.FIELD_NAME_1, field1!!.text.toString())
            CV.put(MyOpenHelper.FIELD_NAME_2, field2!!.text.toString())
            DB.insert(MyOpenHelper.TABLE_NAME, null, CV)
            DB.close()
            field1!!.setText("")
            field2!!.setText("")
        }


        result!!.setText("")
        Log.d("myLogs", "READ FROM DB")
        val DB = myHelper!!.readableDatabase
        val columns = arrayOf("_id", MyOpenHelper.FIELD_NAME_1, MyOpenHelper.FIELD_NAME_2)
        val cursor = DB.query(MyOpenHelper.TABLE_NAME, columns, null, null, null, null, "_id")
        if (cursor != null && cursor.moveToFirst()) {
            do {
                result!!.setText(
                    result!!.text.toString() + "\n" +
                            cursor.getString(0) + ". " + cursor.getString(1) + " " + cursor.getString(
                        2
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        DB.close()

        field1!!.setText("")
        field2!!.setText("")
    }

    private fun deleteDatabase() {
        Log.d("myLogs", "Delete Database")
        val DB = myHelper!!.writableDatabase
        DB.delete(MyOpenHelper.TABLE_NAME, null, null)
        result!!.setText("")
        DB.close()
    }
}

