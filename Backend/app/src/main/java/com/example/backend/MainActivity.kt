package com.example.backend

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.editUsername)
        val password = findViewById<EditText>(R.id.editPassword)
        val button = findViewById<Button>(R.id.btnRegister)
        val textMessage = findViewById<TextView>(R.id.textMessage)
        val goLogin = findViewById<TextView>(R.id.textGoLogin)

        val api = RetrofitClient.instance

        goLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        button.setOnClickListener {

            val user = username.text.toString().trim()
            val pass = password.text.toString()

            if (user.isBlank() || pass.isBlank()) {
                showErrorPopup(
                    title = getString(R.string.error_title),
                    message = getString(R.string.error_empty_fields)
                )
                return@setOnClickListener
            }

            if (pass.length < 6) {
                showErrorPopup(
                    title = getString(R.string.error_title),
                    message = getString(R.string.error_password_short)
                )
                return@setOnClickListener
            }

            val request = RegisterRequest(user, pass)

            textMessage.text = getString(R.string.register_status_loading)

            api.registerUser(request).enqueue(object : Callback<ApiResponse> {

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                    if (response.isSuccessful) {

                        textMessage.text = response.body()?.message ?: getString(R.string.register_status_success)

                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(getString(R.string.register_success_title))
                            .setMessage(getString(R.string.register_success_message))
                            .setPositiveButton(getString(R.string.go_to_login_button)) { _, _ ->
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                finish()
                            }
                            .show()

                    } else {

                        textMessage.text = getString(R.string.register_status_failed)
                        showErrorPopup(
                            title = getString(R.string.error_title),
                            message = getString(R.string.error_user_exists)
                        )

                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                    textMessage.text = getString(R.string.register_status_failed)
                    showErrorPopup(
                        title = getString(R.string.connection_error_title),
                        message = getString(R.string.connection_error_message)
                    )

                }

            })

        }

    }

    private fun showErrorPopup(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.accept_button), null)
            .show()
    }

}