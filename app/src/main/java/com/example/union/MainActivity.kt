package com.example.union

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_findPassword = findViewById<Button>(R.id.btn_findPassword)

        btn_findPassword.setOnClickListener{
            startActivity(Intent(this, findPassword::class.java))
        }
    }
}