package com.example.weeklybite3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Set the Activity title from strings.xml
        title = getString(R.string.title_activity_about)
    }
}
