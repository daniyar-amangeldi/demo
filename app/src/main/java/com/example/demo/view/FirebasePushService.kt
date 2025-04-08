package com.example.demo.view

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.demo.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebasePushService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = NotificationCompat.Builder(this, "location_service_channel")
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notification)
    }
}