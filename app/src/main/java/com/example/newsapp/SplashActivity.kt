package com.example.newsapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivitySplashBinding
import com.example.newsapp.utils.CustomFunction
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : AppCompatActivity() {
    private lateinit var  bindingSplashActivity:  ActivitySplashBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSplashActivity = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplashActivity.root)
        val status = CustomFunction().checkOnboardingStatus(this)



        Timer().schedule(4500) {
            if(status){
                val intent =
                    Intent(
                        this@SplashActivity,
                        NewsActivity::class.java
                    )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            else {
                val intent =
                    Intent(
                        this@SplashActivity,
                        OnboardingAcitivity::class.java
                    )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        }
    }
}