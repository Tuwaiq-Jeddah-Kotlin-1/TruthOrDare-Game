package com.example.truthordaresaudi

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
const val NOTIFICATION_NAME = "NOTIFICATION"

class WorkerNotification(private val context: Context, workParams: WorkerParameters): Worker(context, workParams)  {
    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID).setTicker(NOTIFICATION_NAME)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(NOTIFICATION_NAME)
                .setContentText("Hellooo There, don't you miss us? Come back and play !!")
                .setAutoCancel(true)
                .build()
        //create notification manager
        val notificationManager = NotificationManagerCompat.from(context)

        //call notification manager
        notificationManager.notify(0, notification)
        return Result.success()
    }

}