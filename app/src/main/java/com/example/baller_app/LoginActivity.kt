package com.example.baller_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI
import com.cometchat.pro.uikit.ui_components.groups.group_list.CometChatGroupList
import com.example.baller_app.constans.AppConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity(){
private var et_user_name: EditText? = null
private var loginBtn: TextView? = null
private var signupBtn: TextView? = null
private var progressBar: ProgressBar? = null
private var prefManger: PreferencesManager ?=null
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    prefManger = PreferencesManager.getInstance(this)
    progressBar = findViewById(R.id.createUser_pb)
    et_user_name = findViewById(R.id.et_user_name)
    signupBtn = findViewById(R.id.create_user_btn)
    loginBtn = findViewById(R.id.login_user_btn)
    loginBtn!!.setOnClickListener(
        View.OnClickListener { v: View? -> signInTapped() }
    )
    signupBtn!!.setOnClickListener(
        View.OnClickListener { v: View? ->
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))

        }
    )
}

private fun signInTapped() {
    val user = User()
    user.uid = et_user_name!!.text.toString()
    login(user)
}

private fun login(user: User) {
    progressBar!!.visibility = View.VISIBLE
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Toast.makeText(this@LoginActivity, "Fetching FCM registration token failed", Toast.LENGTH_LONG).show()
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result
        Log.e("=======>  ",token)
        CometChat.login(
            user.uid,
            AppConfig.AppDetails.AUTH_KEY,
            object : CometChat.CallbackListener<User?>() {
                override fun onSuccess(user: User?) {
                    CometChat.registerTokenForPushNotification(token, object : CometChat.CallbackListener<String?>() {
                        override fun onSuccess(s: String?) {
                            progressBar!!.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_LONG).show()
                            prefManger!!.putBoolean("Login",true)
                            startActivity(Intent(this@LoginActivity, CometChatUI::class.java))
                            finish()
                        }

                        override fun onError(e: CometChatException) {
                            progressBar!!.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    })


                }

                override fun onError(e: CometChatException) {
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })

    })
}
}
