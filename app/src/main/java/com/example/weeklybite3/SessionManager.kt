package com.example.weeklybite3

import android.content.Context
import androidx.core.content.edit

object SessionManager {
    private const val PREF = "session_prefs"
    private const val KEY_LOGGED_IN = "logged_in"

    fun setLoggedIn(ctx: Context, value: Boolean) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_LOGGED_IN, value)
        }
    }

    fun isLoggedIn(ctx: Context): Boolean =
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getBoolean(KEY_LOGGED_IN, false)

    fun logout(ctx: Context) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit { clear() }
    }
}
