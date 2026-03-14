package com.example.backend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val username = intent.getStringExtra("username")?.trim()

        val title = findViewById<TextView>(R.id.welcomeTitle)
        val subtitle = findViewById<TextView>(R.id.welcomeSubtitle)
        val goLogin = findViewById<Button>(R.id.btnGoLogin)
        val goRegister = findViewById<Button>(R.id.btnGoRegister)

        if (!username.isNullOrEmpty()) {
            title.text = getString(R.string.welcome_back_title, username)
            subtitle.text = getString(R.string.welcome_back_subtitle)
        }

        goLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        goRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}