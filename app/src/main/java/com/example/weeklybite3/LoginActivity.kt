package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmailLogin)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnLoginAction)
        val tvToSignup = findViewById<TextView>(R.id.tvToSignup)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            // ---- Validation ----
            if (email.isEmpty() || pass.isEmpty()) {
                showToast("Please enter email and password", isError = true)
                return@setOnClickListener
            }

            if (!isValidGmail(email)) {
                showToast("Use a valid @gmail.com address", isError = true)
                return@setOnClickListener
            }

            if (pass.length < 6) { // optional: simple password rule
                showToast("Password must be at least 6 characters", isError = true)
                return@setOnClickListener
            }

            // ---- Auth (replace with real backend/Firebase) ----
            val authOk = true
            if (authOk) {
                showToast("Welcome, $email!")
                SessionManager.setLoggedIn(this, true)
                startActivity(
                    Intent(this, com.example.weeklybite3.plan.MealPlanActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
                finish()
            } else {
                showToast("Invalid email or password", isError = true)
            }
        }

        tvToSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    /** Only accepts addresses like something@gmail.com (case-insensitive). */
    private fun isValidGmail(email: String): Boolean {
        val normalized = email.lowercase()
        return Patterns.EMAIL_ADDRESS.matcher(normalized).matches()
                && normalized.endsWith("@gmail.com")
    }

    // ------- Custom Toast (success/error) -------
    private fun showToast(message: String, isError: Boolean = false) {
        val view = LayoutInflater.from(this).inflate(R.layout.view_toast, null)
        val tv = view.findViewById<TextView>(R.id.tvMessage)
        val root = view.findViewById<android.widget.LinearLayout>(R.id.toast_root)

        tv.text = message
        root.setBackgroundResource(
            if (isError) R.drawable.bg_toast_error else R.drawable.bg_toast_success
        )

        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setView(view)
            setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 140)
            show()
        }
    }
}
