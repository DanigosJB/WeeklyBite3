package com.example.weeklybite3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ✅ Ask for notification permission (Android 13+)
        ensureNotifPermission()

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

            if (pass.length < 6) { // simple password rule
                showToast("Password must be at least 6 characters", isError = true)
                return@setOnClickListener
            }

            // ---- Simulated Authentication ----
            val authOk = true
            if (authOk) {
                showToast("Welcome, $email!")
                SessionManager.setLoggedIn(this, true)

                // ✅ Save email so Settings / Account screens can display it
                val prefs = getSharedPreferences("weeklybite_settings", MODE_PRIVATE)
                prefs.edit().putString("last_login_email", email).apply()

                // ✅ Show welcome notification (intent + user communication)
                NotificationHelper.showLoginSuccess(this, email)

                // ✅ Explicit intent to MealPlanActivity
                startActivity(
                    Intent(this, MealPlanActivity::class.java).apply {
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

    /**
     * Ask for POST_NOTIFICATIONS permission on Android 13+.
     * This allows NotificationHelper to show system notifications.
     */
    private fun ensureNotifPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    999
                )
            }
        }
    }

    /** Custom Toast (success/error) */
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
