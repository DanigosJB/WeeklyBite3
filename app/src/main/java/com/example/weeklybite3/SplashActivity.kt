package com.example.weeklybite3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.weeklybite3.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val motion = binding.motionLayout

        // Auto-play the transition from full cover -> pill button
        motion.post { motion.transitionToEnd() }

        // When animation finishes, the pill acts like a CTA button
        motion.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                if (currentId == com.example.weeklybite3.R.id.end) {
                    binding.coverCard.setOnClickListener {
                        startActivity(Intent(this@SplashActivity, LoginSignupActivity::class.java))
                        finish()
                    }
                }
            }
            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionTrigger(p0: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
        })
    }
}
