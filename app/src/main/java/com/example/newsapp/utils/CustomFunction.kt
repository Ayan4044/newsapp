package com.example.newsapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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

    fun onboardhingcheck(context: Context, status: Boolean){
        val sharedPreferences: SharedPreferences =
           context.getSharedPreferences("newsshared", MODE_PRIVATE)
        val myEdit: SharedPreferences.Editor = sharedPreferences.edit()
        myEdit.putBoolean("onboarding",status)
        myEdit.apply()
    }


    fun checkOnboardingStatus(context: Context): Boolean {
        val sh: SharedPreferences = context.getSharedPreferences("newsshared", MODE_PRIVATE)
        return sh.getBoolean("onboarding", false)
    }



}