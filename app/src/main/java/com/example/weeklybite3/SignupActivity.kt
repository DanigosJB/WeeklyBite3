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

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etEmail = findViewById<EditText>(R.id.etEmailSignup)
        val etPass = findViewById<EditText>(R.id.etPasswordSignup)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPasswordSignup)
        val btnSignup = findViewById<Button>(R.id.btnSignupAction)
        val tvToLogin = findViewById<TextView>(R.id.tvToLogin)

        btnSignup.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            // ✅ Validation
            if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                showToast("Please fill all fields", isError = true)
                return@setOnClickListener
            }

            if (!isValidGmail(email)) {
                showToast("Use a valid @gmail.com address", isError = true)
                return@setOnClickListener
            }

            if (pass.length < 6) {
                showToast("Password must be at least 6 characters", isError = true)
                return@setOnClickListener
            }

            if (pass != confirm) {
                showToast("Passwords do not match", isError = true)
                return@setOnClickListener
            }

            // ✅ Simulated successful signup (replace with Firebase/API later)
            showToast("Account created for $email!")

            // Navigate to login after successful signup
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // ✅ Allow only Gmail addresses
    private fun isValidGmail(email: String): Boolean {
        val normalized = email.lowercase()
        return Patterns.EMAIL_ADDRESS.matcher(normalized).matches() &&
                normalized.endsWith("@gmail.com")
    }

    // ✅ Custom teal/red toast
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
