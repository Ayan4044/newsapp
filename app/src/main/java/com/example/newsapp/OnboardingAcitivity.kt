package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.utils.CustomFunction
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class OnboardingAcitivity : AppIntro() {
    private  lateinit var bindingOnboardingAcitivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingOnboardingAcitivity = ActivityMainBinding.inflate(layoutInflater)
       // setContentView(bindingOnboardingAcitivity.root)
        setTransformer(AppIntroPageTransformerType.Fade)

        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.createInstance(
            title =getString(R.string.intro_one_title),
            description =getString(R.string.intro_one_description),
                imageDrawable =R.drawable.onboarding_one,
                titleColorRes  = R.color.teal_200,
                backgroundColorRes= R.color.lightblue,
                        descriptionColorRes = R.color.whiteshade
            ))
        addSlide(AppIntroFragment.createInstance(
            title =getString(R.string.intro_two_title),
            description =getString(R.string.intro_two_description),
            imageDrawable =R.drawable.onboarding_two,
            backgroundColorRes  = R.color.lightgreen,

            descriptionColorRes = R.color.whiteshade

        ))

        addSlide(AppIntroFragment.createInstance(
            title =getString(R.string.intro_three_title),
            description =getString(R.string.intro_three_description),
            imageDrawable =R.drawable.onboarding_three,
            backgroundColorRes  = R.color.lightred,

            descriptionColorRes = R.color.whiteshade

        ))
    }


    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        movetoMain()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        movetoMain()

    }

    private fun movetoMain(){
        CustomFunction().onboardhingcheck(this@OnboardingAcitivity, true)
        val intent =
            Intent(
                this@OnboardingAcitivity,
                NewsActivity::class.java
            )
        startActivity(intent)


        finish()
    }
}