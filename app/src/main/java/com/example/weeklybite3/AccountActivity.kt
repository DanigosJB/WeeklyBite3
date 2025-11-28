package com.example.weeklybite3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weeklybite3.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar with back arrow
        setSupportActionBar(binding.toolbarAccount)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // ✅ Use string resource instead of hardcoded "Account"
        supportActionBar?.title = getString(R.string.title_activity_account)
        binding.toolbarAccount.setNavigationOnClickListener { finish() }

        // ✅ Get saved email from SessionManager
        val savedEmail = SessionManager.getEmail(this)
        if (savedEmail.isNullOrBlank()) {
            binding.tvUserName.text = "Guest User"
            binding.tvEmailValue.text = "Not logged in"
            binding.tvMemberSince.text = "Member since 2025"
        } else {
            binding.tvEmailValue.text = savedEmail
            val namePart = savedEmail.substringBefore("@")
            val displayName = namePart.replaceFirstChar { it.uppercase() }
            binding.tvUserName.text = displayName
            binding.tvMemberSince.text = "Member since 2025"
        }

        // Change password just shows a toast for now
        binding.btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Change password feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Contact support via implicit email intent
        binding.btnContactSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("support@weeklybite.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Weekly Bite - Account Help")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
