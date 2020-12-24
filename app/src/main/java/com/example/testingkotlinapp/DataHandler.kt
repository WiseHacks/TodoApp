package com.example.testingkotlinapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DataHandler(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," + KEY_IS_CHECKED + " TEXT);"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }
    fun addTodo(t: Todo): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, t.title)
        values.put(KEY_IS_CHECKED, t.isChecked)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getTodoList() : MutableList<Todo>{
        var list  = ArrayList<Todo>()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var todo = Todo()
                todo.id = result.getString(result.getColumnIndex(KEY_ID)).toInt()
                todo.title = result.getString(result.getColumnIndex(KEY_TITLE))
                todo.isChecked = result.getString(result.getColumnIndex(KEY_IS_CHECKED))
                list.add(todo)
                Log.d("L1", " reading ${todo.id} -- ${todo.title} -- ${todo.isChecked}")

            }while (result.moveToNext())
        }
        result.close()
        db.close()
//        list.sortBy {
//            it.isChecked == "done"
//        }
        return list
    }
    fun updateTodo(t: Todo): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, t.title)
        values.put(KEY_IS_CHECKED, t.isChecked)
        Log.d("hhh","ok ${t.title} --- ${t.isChecked}")
        val _success = db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(t.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
    fun deleteTodo(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteDoneTodos() {
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var todo = Todo()
                todo.id = result.getString(result.getColumnIndex(KEY_ID)).toInt()
                todo.title = result.getString(result.getColumnIndex(KEY_TITLE))
                todo.isChecked = result.getString(result.getColumnIndex(KEY_IS_CHECKED))
                if(todo.isChecked == "done"){
                    deleteTodo(todo.id)
                }

            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }

    companion object {

        private const val DB_NAME = "mydb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "todo_table"
        private const val KEY_ID = "todo_id"
        private const val KEY_TITLE = "todo_title"
        private const val KEY_IS_CHECKED = "todo_is_checked"
    }

}
