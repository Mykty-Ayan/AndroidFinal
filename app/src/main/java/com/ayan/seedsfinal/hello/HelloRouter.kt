package com.ayan.seedsfinal.hello

import android.content.Context
import android.content.Intent
import com.ayan.seedsfinal.login.LoginActivity
import com.ayan.seedsfinal.login.SignUpActivity

class HelloRouter {
    fun createSignUpIntent(context: Context): Intent =
        Intent(context, SignUpActivity::class.java)

    fun createLoginIntent(context: Context): Intent =
        Intent(context, LoginActivity::class.java)
}