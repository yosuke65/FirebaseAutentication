package com.example.firebaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        init()
    }

    private fun init() {

        auth = FirebaseAuth.getInstance()

        edit_text_email_user.setText(auth.currentUser?.email.toString())

        button_delete_user.setOnClickListener(this)
        button_logout.setOnClickListener(this)
        button_update_email.setOnClickListener(this)
        button_update_password.setOnClickListener(this)
        button_send_reset_email.setOnClickListener(this)
        button_edit_email.setOnClickListener(this)
        button_edit_password.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_update_email -> {
                updateCurrentUserEmail()
            }
            R.id.button_update_password -> {
                updateCurrentUserPassword()
            }

            R.id.button_delete_user -> {
                deleteCurrentUser()
            }
            R.id.button_send_reset_email -> {
                sendPasswordResetEmail()

            }
            R.id.button_logout -> {
                logout()
            }
            R.id.button_edit_email->{
                edit_text_email_user.isEnabled = !edit_text_email_user.isEnabled
            }
            R.id.button_edit_password->{
                edit_text_password_user.isEnabled = !edit_text_password_user.isEnabled
            }

        }
    }

    private fun updateCurrentUserEmail() {
        if (edit_text_email_user.text.isNullOrEmpty()) {
            edit_text_email_user.setError("Please enter email")
            edit_text_email_user.requestFocus()
        } else {
            auth.currentUser!!.updateEmail(edit_text_email_user.text.toString())
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Email Updated to ${edit_text_email_user.text.toString()}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Email Updated Failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                })
        }


    }

    private fun updateCurrentUserPassword() {
        if(edit_text_password_user.text.isNullOrEmpty()){
            edit_text_password_user.setError("Enter password")
            edit_text_password_user.requestFocus()
        }else{
            auth.currentUser!!.updatePassword(edit_text_password_user.text.toString())
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Password Updated to ${edit_text_password_user.text.toString()}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Password Updated Failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                })
        }


    }

    private fun deleteCurrentUser() {
        auth.currentUser!!.delete().addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(task: Task<Void>) {
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "User Deleted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "User Delete Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

    }

    private fun sendPasswordResetEmail() {
        auth.sendPasswordResetEmail(auth.currentUser?.email.toString())
            .addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "The link has been send to ${auth.currentUser?.email}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Failed to send email to ${auth.currentUser?.email}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            })

    }

    private fun logout() {
        auth.signOut()
        finish()
    }
}