package com.example.baller_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI

class SplashActivity : AppCompatActivity() {
    private var prefManger: PreferencesManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefManger = PreferencesManager.getInstance(this)
        Thread.sleep(2000)
        if(prefManger!!.getBoolen("Login")){
            startActivity(Intent(this@SplashActivity, CometChatUI::class.java))
        }
        else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}