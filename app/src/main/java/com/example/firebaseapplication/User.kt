package com.example.firebaseapplication

import java.io.Serializable

data class User(
    var email:String,
    var password:String?
):Serializable {
    companion object{
        const val KEY = "user_key"
    }
}