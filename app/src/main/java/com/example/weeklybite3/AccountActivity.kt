package com.example.weeklybite3.plan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weeklybite3.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        supportActionBar?.title = "Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
