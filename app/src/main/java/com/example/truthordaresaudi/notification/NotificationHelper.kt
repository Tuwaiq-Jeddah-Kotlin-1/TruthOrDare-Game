package com.example.truthordaresaudi.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.truthordaresaudi.CHANNEL_ID
import com.example.truthordaresaudi.NOTIFICATION_NAME


class NotificationHelper(): Application() {
    override fun onCreate() {
        super.onCreate()
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, NOTIFICATION_NAME, importance)
        val notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
