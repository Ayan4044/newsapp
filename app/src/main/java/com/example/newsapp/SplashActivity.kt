package com.example.newsapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : AppCompatActivity() {
    private lateinit var  bindingSplashActivity:  ActivitySplashBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSplashActivity = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplashActivity.root)


        val typeface = resources.getFont(R.font.proxima_regular)
        bindingSplashActivity.textView.typeface = typeface

        Timer().schedule(3000) {
            val intent =
                Intent(
                    this@SplashActivity,
                    OnboardingAcitivity::class.java
                )
            startActivity(intent)
            finish()
        }
    }
}