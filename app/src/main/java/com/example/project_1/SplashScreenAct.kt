package com.example.project_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreenAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
//        val bottomanimation= AnimationUtils.loadAnimation(this,R.anim.bottom_anim)
//        val textView=findViewById<TextView>(R.id.textView)
//        textView.animation=bottomanimation
        //hide taskbar
        supportActionBar?.hide()
        //hop to next screen
        val intent=Intent(this,MainActivity::class.java)
        Timer().schedule(timerTask {
            startActivity(intent)
            finish()
        }, 3800)

    }
}