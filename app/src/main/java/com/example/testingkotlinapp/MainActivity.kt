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
    private lateinit var TodoAdapter:CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TodoAdapter = CustomAdapter(this)
        rcView.adapter = TodoAdapter
        rcView.layoutManager = LinearLayoutManager(this)
        btnAdd.setOnClickListener {
            var t : Todo = Todo()
            var string:String = ""
            for (i in edtTodo.text.toString().indices){
                if(string.isEmpty()){
                    if(edtTodo.text.toString().elementAt(i) != ' ')string += edtTodo.text.toString().elementAt(i)
                }
                else if(string.elementAt(string.length - 1) != ' '){
                    string += edtTodo.text.toString().elementAt(i)
                }
                else if(string.elementAt(string.length - 1) == ' '){
                    if(edtTodo.text.toString().elementAt(i) != ' ')string += edtTodo.text.toString().elementAt(i)
                }
            }
            if(string != "") {
                t.title = string
                t.isChecked = "undone"
                edtTodo.text.clear()
                TodoAdapter.addTodo(t)
                this.recreate()
            }
            else Toast.makeText(this, "enter valid text", Toast.LENGTH_SHORT).show()
        }
        btnDelete.setOnClickListener {
            TodoAdapter.Deletedone()
            this.recreate()
        }
    }
}