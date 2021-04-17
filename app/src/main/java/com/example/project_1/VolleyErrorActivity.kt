package com.example.project_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class VolleyErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_error)
        fun retry()
        {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}