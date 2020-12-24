package com.example.testingkotlinapp

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*

class CustomAdapter(
        private val todoList : MutableList<Todo>
) : RecyclerView.Adapter<CustomAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.todo_item,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       val cur = todoList[position]
        holder.itemView.apply{
            txtTodo.text = cur.title
            checkTodo.isChecked = cur.isChecked
            StrikeThru(txtTodo,checkTodo.isChecked)
            checkTodo.setOnCheckedChangeListener { _, _ ->
                cur.isChecked = checkTodo.isChecked
                Log.d("check","checking ${cur.title}: ${cur.isChecked}")
                StrikeThru(txtTodo,cur.isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    fun StrikeThru(txtTodo: TextView, isChecked : Boolean){
        if(isChecked){
            txtTodo.paintFlags  =txtTodo.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else{
            txtTodo.paintFlags = 0
        }
    }
    fun addTodo(todo :Todo){
        todoList.add(todo)
        notifyItemInserted(todoList.size - 1)
    }
    fun Deletedone(){
        todoList.removeAll {
            it.isChecked
        }
        notifyDataSetChanged()
    }
}