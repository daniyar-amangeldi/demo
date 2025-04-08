package com.example.demo.view.util

import android.content.Context
import com.example.demo.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigManager(context: Context) {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 1
    }

    fun start() {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate().addOnCompleteListener {

        }
    }

    fun getBoolean(key: String) = remoteConfig.getBoolean(key)
}