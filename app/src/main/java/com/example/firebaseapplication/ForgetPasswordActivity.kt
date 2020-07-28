package com.example.firebaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        init()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        button_reset_password.setOnClickListener {
            sendPasswordResetResuest()
        }
        button_home.setOnClickListener {
            finish()
        }
    }

    private fun sendPasswordResetResuest() {
        if (edit_text_email.text.isNullOrEmpty()) {
            edit_text_email.setError("Enter email")
            edit_text_email.requestFocus()
        } else {
            auth.sendPasswordResetEmail(edit_text_email.text.toString())
                .addOnCompleteListener(this, object : OnCompleteListener<Void> {
                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "The link has been sent",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Request Failed", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                })
        }
    }
}