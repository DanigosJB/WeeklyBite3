package com.example.weeklybite3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "weeklybite_login"
    private const val CHANNEL_NAME = "Weekly Bite Login"
    private const val NOTIF_ID_LOGIN = 1001

    private fun ensureChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mgr = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mgr.createNotificationChannel(channel)
        }
    }

    /**
     * Called after a successful login.
     * Shows a notification that opens MealPlanActivity when tapped.
     */
    fun showLoginSuccess(ctx: Context, email: String) {
        ensureChannel(ctx)

        // Tap notification -> opens MealPlanActivity
        val intent = Intent(ctx, MealPlanActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingFlags =
            PendingIntent.FLAG_UPDATE_CURRENT or
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE else 0)

        val pi = PendingIntent.getActivity(
            ctx,
            0,
            intent,
            pendingFlags
        )

        val notif = NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // or another icon
            .setContentTitle("Welcome to Weekly Bite")
            .setContentText("Logged in as $email")
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        // ✅ Only call notify() if we actually have POST_NOTIFICATIONS permission
        if (
            Build.VERSION.SDK_INT < 33 ||
            ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(ctx).notify(NOTIF_ID_LOGIN, notif)
        }
        // If user denied permission, we just silently skip the notification.
    }
}
