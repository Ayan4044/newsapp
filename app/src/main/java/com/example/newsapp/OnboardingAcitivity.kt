package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.ActivityMainBinding
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment

class OnboardingAcitivity : AppIntro() {
    private  lateinit var bindingOnboardingAcitivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingOnboardingAcitivity = ActivityMainBinding.inflate(layoutInflater)
       // setContentView(bindingOnboardingAcitivity.root)

        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.createInstance(
            title = "Welcome...",
            description = "This is the first slide of the example",
                imageDrawable =R.drawable.onboarding_one
            ))
        addSlide(AppIntroFragment.createInstance(
            title = "...Let's get started!",
            description = "This is the last slide, I won't annoy you more :)",
            imageDrawable =R.drawable.onboarding_two

        ))

        addSlide(AppIntroFragment.createInstance(
            title = "...Let's get started!",
            description = "This is the last slide, I won't annoy you more :)",
            imageDrawable =R.drawable.onboarding_three
        ))
    }


    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }
}