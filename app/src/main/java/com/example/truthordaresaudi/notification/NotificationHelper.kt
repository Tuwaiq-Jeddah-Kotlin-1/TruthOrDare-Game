package com.example.truthordaresaudi.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.truthordaresaudi.CHANNEL_ID
import com.example.truthordaresaudi.NOTIFICATION_ID
import com.example.truthordaresaudi.NOTIFICATION_NAME
import com.example.truthordaresaudi.ui.SplashScreen



class NotificationHelper(): Application() {
    override fun onCreate() {
        super.onCreate()
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, NOTIFICATION_NAME, importance)
        val notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
