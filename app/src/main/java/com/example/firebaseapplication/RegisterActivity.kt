package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var email: String = ""
    private var password: String = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()

        button_register.setOnClickListener {
            register()
        }
    }

    private fun register() {

        if (validateEmail() && validatePassword()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener(object : OnCompleteListener<Void> {
                                    override fun onComplete(task: Task<Void>) {
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Sent verification email",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            startLoginActivity()
                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                task.exception?.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })
                            Toast.makeText(applicationContext, "Registered", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun validatePassword(): Boolean {
        return if (edit_view_password.text.isNullOrEmpty()) {
            edit_view_password.setError("Please Enter password")
            edit_view_password.requestFocus()
            false
        } else {
            password = edit_view_password.text.toString()
            true
        }
    }

    private fun validateEmail(): Boolean {
        if (edit_view_email.text.isNullOrEmpty()) {
            edit_view_email.setError("Please Enter email")
            edit_view_email.requestFocus()
            return false
        } else {
            email = edit_view_email.text.toString()
            return true
        }
    }
}