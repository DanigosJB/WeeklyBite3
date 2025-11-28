package com.example.weeklybite3

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME = "weeklybite_session"
    private const val KEY_LOGGED_IN = "logged_in"
    private const val KEY_EMAIL = "user_email"

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setLoggedIn(ctx: Context, value: Boolean) {
        prefs(ctx).edit().putBoolean(KEY_LOGGED_IN, value).apply()
    }

    fun isLoggedIn(ctx: Context): Boolean =
        prefs(ctx).getBoolean(KEY_LOGGED_IN, false)

    fun setEmail(ctx: Context, email: String) {
        prefs(ctx).edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(ctx: Context): String? =
        prefs(ctx).getString(KEY_EMAIL, null)

    fun logout(ctx: Context) {
        // Clear everything on logout
        prefs(ctx).edit().clear().apply()
    }
}
