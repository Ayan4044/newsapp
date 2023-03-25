package com.example.newsapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.newsapp.R

class CustomFunction {

    fun fragmentReplace(fragmentact: FragmentActivity, fragment: Fragment){
        fragmentact.supportFragmentManager.beginTransaction()

            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_out_right, R.anim.slide_in_right)
            .replace(
                R.id.fragment_container,
                fragment
            ).addToBackStack(null).commit()
    }



}