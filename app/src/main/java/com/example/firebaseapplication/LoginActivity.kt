package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var email: String = ""
    private var password: String = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {

        auth = FirebaseAuth.getInstance()

        button_login.setOnClickListener {
            login()
        }

        text_view_forgot_password.setOnClickListener{
           startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

    }


    private fun login() {

        if (validateEmail() && validatePassword()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    checkUserEmailValification()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Email and password don't match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkUserEmailValification() {
        if (auth.currentUser!!.isEmailVerified) {
            Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
            startUserProfileActivity()
        } else {
            Toast.makeText(applicationContext, "Please verify your email", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun startUserProfileActivity() {
        var myIntent = Intent(this, UserProfileActivity::class.java)
        var user = User(email, password)
        myIntent.putExtra(User.KEY, user)
        startActivity(myIntent)
    }

    private fun validatePassword(): Boolean {
        return if (edit_view_email.text.isNullOrEmpty()) {
            edit_view_email.setError("Please enter email")
            edit_view_email.requestFocus()
            false
        } else {
            email = edit_view_email.text.toString()
            true
        }
    }

    private fun validateEmail(): Boolean {
        return if (edit_view_password.text.isNullOrEmpty()) {
            edit_view_password.setError("Please enter password")
            edit_view_password.requestFocus()
            false
        } else {
            password = edit_view_password.text.toString()
            true
        }
    }
}