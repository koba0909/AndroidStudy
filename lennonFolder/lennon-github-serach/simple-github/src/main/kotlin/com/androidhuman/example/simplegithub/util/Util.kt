package com.androidhuman.example.simplegithub.util

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideSoftKeyboard(activity: AppCompatActivity, view: View) {
    (activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(view.windowToken, 0)
    }
}