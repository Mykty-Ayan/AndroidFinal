package com.ayan.seedsfinal.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayan.seedsfinal.NavigationActivity
import com.ayan.seedsfinal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var signUpTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun bindViews() {
        loginButton = findViewById(R.id.activity_login_button)
        loginEmailEditText = findViewById(R.id.login_email_edit_text)
        loginPasswordEditText = findViewById(R.id.login_password_edit_text)
        signUpTextView = findViewById(R.id.login_sign_up_text_view)

        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        loginButton.setOnClickListener {
            logIn()
        }
    }

    private fun logIn() {
        if (loginEmailEditText.text.toString().isEmpty()) {
            loginEmailEditText.error = "You forgot to enter your email"
            loginEmailEditText.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmailEditText.text.toString()).matches()) {
            loginEmailEditText.error = "Enter a valid email"
            loginEmailEditText.requestFocus()
            return
        }
        if (loginPasswordEditText.text.toString().isEmpty()) {
            loginPasswordEditText.error = "You forgot to enter your password"
            loginPasswordEditText.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(
            loginEmailEditText.text.toString(),
            loginPasswordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext, "Oops! Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Verify your email address",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Oops! Login failed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}