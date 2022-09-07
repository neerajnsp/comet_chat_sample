package com.example.baller_app

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.example.baller_app.constans.AppConfig


class RegistrationActivity : AppCompatActivity() {
    private var et_user_name: EditText? = null
    private var name: EditText? = null
    private var createUserBtn: TextView? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        et_user_name = findViewById(R.id.et_user_name)
        name = findViewById(R.id.etName)
        progressBar = findViewById(R.id.createUser_pb)
        createUserBtn = findViewById(R.id.create_user_btn)
        createUserBtn!!.setOnClickListener(
            View.OnClickListener { v: View? -> signUpTapped() }
        )
    }

    private fun signUpTapped() {
        val user = User()
        user.uid = et_user_name!!.text.toString()
        user.name = name!!.text.toString()
        registerUser(user)
    }

    private fun registerUser(user: User) {
        progressBar!!.visibility = View.VISIBLE
        CometChat.createUser(
            user,
            AppConfig.AppDetails.AUTH_KEY,
            object : CallbackListener<User>() {
                override fun onSuccess(user: User) {
                    progressBar!!.visibility = View.GONE
                    login(user)
                }

                override fun onError(e: CometChatException) {
                    progressBar!!.visibility = View.GONE
                    createUserBtn!!.isClickable = true
                    Toast.makeText(this@RegistrationActivity, e.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun login(user: User) {
        progressBar!!.visibility = View.VISIBLE
        CometChat.login(
            user.uid,
            AppConfig.AppDetails.AUTH_KEY,
            object : CallbackListener<User?>() {
                override fun onSuccess(user: User?) {
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(this@RegistrationActivity, "Login Done", Toast.LENGTH_LONG)
                        .show()
//                    startActivity(
//                        Intent(
//                            this@RegistrationActivity,
//                            ConversationsActivity::class.java
//                        )
//                    )
                }

                override fun onError(e: CometChatException) {
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(this@RegistrationActivity, e.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}