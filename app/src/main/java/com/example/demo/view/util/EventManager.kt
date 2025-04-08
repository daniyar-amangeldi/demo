package com.example.demo.view.util

import android.content.Context
import androidx.core.bundle.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

class EventManager(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun log(eventName: String, params: Bundle = bundleOf()) {
        firebaseAnalytics.logEvent(eventName, params)
    }
}