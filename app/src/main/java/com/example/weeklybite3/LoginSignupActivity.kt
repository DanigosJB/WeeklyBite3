package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginSignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ If already logged in, skip straight to MealPlanActivity
        if (SessionManager.isLoggedIn(this)) {
            startActivity(
                Intent(this, MealPlanActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
            finish()
            return
        }

        // Otherwise show the login/signup choice screen
        setContentView(R.layout.activity_login_signup)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        // You can replace these with string resources later if you like
        btnLogin.text = "Login"
        btnSignup.text = "Sign Up"

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
