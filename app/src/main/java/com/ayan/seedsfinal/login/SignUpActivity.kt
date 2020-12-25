package com.ayan.seedsfinal.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayan.seedsfinal.R
import com.ayan.seedsfinal.hello.HelloRouter
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SignUpActivity"

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var signUpEmailEditText: EditText
    private lateinit var signUpPasswordEditText: EditText
    private lateinit var loginTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        bindViews()
        auth = FirebaseAuth.getInstance()
    }

    private fun bindViews() {
        signUpButton = findViewById(R.id.activity_sign_up_button)
        signUpEmailEditText = findViewById(R.id.sign_up_email_edit_text)
        signUpPasswordEditText = findViewById(R.id.sign_up_password_edit_text)
        loginTextView = findViewById(R.id.sign_up_login_text_view)

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        if (signUpEmailEditText.text.toString().isEmpty()) {
            signUpEmailEditText.error = "You forgot to enter your email"
            signUpEmailEditText.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmailEditText.text.toString()).matches()) {
            signUpEmailEditText.error = "Enter a valid email"
            signUpEmailEditText.requestFocus()
            return
        }
        if (signUpPasswordEditText.text.toString().isEmpty()) {
            signUpPasswordEditText.error = "You forgot to enter your password"
            signUpEmailEditText.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(
            signUpEmailEditText.text.toString(),
            signUpPasswordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(HelloRouter().createLoginIntent(this))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Oops! Sign up failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}