package com.ayan.seedsfinal.hello

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ayan.seedsfinal.R

class HelloActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        bindViews()
    }

    private fun bindViews() {
        signUpButton = findViewById(R.id.activity_hello_sign_up_button)
        loginButton = findViewById(R.id.activity_hello_log_in_button)

        signUpButton.setOnClickListener {
            startActivity(HelloRouter().createSignUpIntent(this))
        }
        loginButton.setOnClickListener {
            startActivity(HelloRouter().createLoginIntent(this))
        }
    }
}