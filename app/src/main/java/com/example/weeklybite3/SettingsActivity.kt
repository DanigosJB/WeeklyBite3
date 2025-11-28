package com.example.weeklybite3

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weeklybite3.databinding.ActivitySettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val prefs by lazy { getSharedPreferences("weeklybite_settings", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar with back arrow
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ✅ Use string resource instead of hardcoded "Settings"
        supportActionBar?.title = getString(R.string.title_activity_settings)

        // Show current email if you want (fallback text if not logged in)
        val email = prefs.getString("last_login_email", "guest@gmail.com")
        binding.tvAccountEmail.text = "Signed in as: $email"

        // Load saved switches
        val notifEnabled = prefs.getBoolean("notif_enabled", true)
        val remindersEnabled = prefs.getBoolean("reminders_enabled", false)

        binding.switchNotifications.isChecked = notifEnabled
        binding.switchReminders.isChecked = remindersEnabled

        // Toggle: Login notifications
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notif_enabled", isChecked).apply()
            Snackbar.make(
                binding.root,
                if (isChecked) "Login notifications enabled" else "Login notifications disabled",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        // Toggle: Daily reminder (placeholder – just save and show message)
        binding.switchReminders.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("reminders_enabled", isChecked).apply()
            Snackbar.make(
                binding.root,
                if (isChecked) "Daily reminders enabled" else "Daily reminders disabled",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        // Reset to default settings
        binding.btnResetSettings.setOnClickListener {
            prefs.edit()
                .putBoolean("notif_enabled", true)
                .putBoolean("reminders_enabled", false)
                .apply()

            binding.switchNotifications.isChecked = true
            binding.switchReminders.isChecked = false

            Snackbar.make(binding.root, "Settings reset to defaults", Snackbar.LENGTH_SHORT).show()
        }
    }

    // Handle toolbar back arrow
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
