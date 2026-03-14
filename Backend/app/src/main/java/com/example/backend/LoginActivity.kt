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

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.loginUsername)
        val password = findViewById<EditText>(R.id.loginPassword)
        val button = findViewById<Button>(R.id.btnLogin)
        val goRegister = findViewById<TextView>(R.id.textGoRegister)
        val status = findViewById<TextView>(R.id.loginStatus)

        val api = RetrofitClient.instance

        goRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        button.setOnClickListener {

            val user = username.text.toString().trim()
            val pass = password.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                showErrorPopup(
                    title = getString(R.string.error_title),
                    message = getString(R.string.error_empty_fields)
                )
                return@setOnClickListener
            }

            val request = LoginRequest(
                user,
                pass
            )

            status.text = getString(R.string.login_status_loading)

            api.loginUser(request).enqueue(object : Callback<ApiResponse> {

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                    if (response.isSuccessful) {

                        val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)

                        intent.putExtra("username", request.username)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                        startActivity(intent)
                        finish()

                    } else {

                        status.text = getString(R.string.login_status_failed)
                        showErrorPopup(
                            title = getString(R.string.error_title),
                            message = getString(R.string.error_invalid_credentials)
                        )

                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                    status.text = getString(R.string.login_status_failed)
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
