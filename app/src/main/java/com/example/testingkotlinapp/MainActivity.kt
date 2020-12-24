package com.example.testingkotlinapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter : CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter  = CustomAdapter(mutableListOf())  //initialize on create
        rcView.adapter = todoAdapter
        rcView.layoutManager = LinearLayoutManager(this)
        btnAdd.setOnClickListener {
            val todoTitle  = edtTodo.text.toString()
            if(todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle,false)
                todoAdapter.addTodo(todo)
                edtTodo.text.clear()
            }
        }
        btnDelete.setOnClickListener {
            todoAdapter.Deletedone()
        }

    }
}