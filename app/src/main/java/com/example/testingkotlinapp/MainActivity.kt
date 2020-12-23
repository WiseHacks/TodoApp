package com.example.testingkotlinapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPress.setOnClickListener{
            Log.d("LOG","pressed")
            Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show()
            val message = edtText.text.toString()
            Log.d("check","edtTxt : $message")
            val intent = Intent(this,MessageActivity::class.java)
            intent.putExtra("your_message",message)
            startActivity(intent)
        }
        btnShare.setOnClickListener {
            val message = edtText.text.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,message)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,"Share to : "))
            /*
            * lets see
            * */
            /*intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel:+91$message")
            startActivity(intent)*/
        }

    }
}