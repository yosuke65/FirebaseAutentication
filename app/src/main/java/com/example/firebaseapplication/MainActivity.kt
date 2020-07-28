package com.example.firebaseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
         startActivity(Intent(this, UserProfileActivity::class.java))
        }
        button_register.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_login.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}