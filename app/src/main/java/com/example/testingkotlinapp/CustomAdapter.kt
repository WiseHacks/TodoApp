package com.example.testingkotlinapp

import android.content.Context
import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*

class CustomAdapter(
        private val context: Context
) : RecyclerView.Adapter<CustomAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var db = DataHandler(context)
    var todoList = db.getTodoList()

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
            var cnt = (position+1)
            textCount.text = "$cnt."
            txtTodo.text = cur.title
            when {
                cnt%3==1 -> txtTodo.setTextColor(Color.parseColor("#3C008F"))
                cnt%3==2 -> txtTodo.setTextColor(Color.parseColor("#932502"))
                cnt%3==0 -> txtTodo.setTextColor(Color.parseColor("#004A41"))
            }
            var color = txtTodo.currentTextColor
            checkStatus(txtTodo,cur.isChecked,color)
            db.updateTodo(cur)
            if(cur.isChecked == "undone"){
                btnDoneTodo.isEnabled = true
                btnUndoneTodo.isEnabled = false
            }
            else{
                btnDoneTodo.isEnabled = false
                btnUndoneTodo.isEnabled = true
            }
            btnDoneTodo.setOnClickListener {
                cur.isChecked = "done"
                db.updateTodo(cur)
                btnDoneTodo.isEnabled = false
                btnUndoneTodo.isEnabled = true
                checkStatus(txtTodo,cur.isChecked,color)
            }
            btnUndoneTodo.setOnClickListener {
                cur.isChecked = "undone"
                db.updateTodo(cur)
                btnDoneTodo.isEnabled = true
                btnUndoneTodo.isEnabled = false
                checkStatus(txtTodo,cur.isChecked,color)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    fun checkStatus(txtTodo: TextView, isChecked : String , color : Int){

        if(isChecked == "done"){
            txtTodo.setTextColor(Color.parseColor("#B6B1B1"))
        }
        else{
            txtTodo.setTextColor(color)
        }
    }
    fun addTodo(todo :Todo){
        todoList.add(todo)
        db.addTodo(todo)
        notifyItemInserted(todoList.size - 1)
    }
    fun Deletedone(){
        db.deleteDoneTodos()
        notifyDataSetChanged()
    }
}