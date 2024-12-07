package com.example.demo.view.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserData(context: Context) {

    companion object {
        private const val AUTHORIZATION_PREFERENCE = "com.example.demo.authorization"
        private const val IS_AUTHORIZED = "is_authorized"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(AUTHORIZATION_PREFERENCE, Context.MODE_PRIVATE)

    fun isAuthorized() = preferences.getBoolean(IS_AUTHORIZED, false)

    fun setAuthorizationState(isAuthorized: Boolean) = preferences.edit {
        putBoolean(IS_AUTHORIZED, isAuthorized)
    }
}