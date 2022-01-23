package com.example.truthordaresaudi.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.truthordaresaudi.CHANNEL_ID
import com.example.truthordaresaudi.MESSAGE
import com.example.truthordaresaudi.NOTIFICATION_ID
import com.example.truthordaresaudi.TITLE
import com.example.truthordaresaudi.ui.MyMainActivity

class WorkerNotification(private val context: Context, workParams: WorkerParameters): Worker(context, workParams)  {
    override fun doWork(): Result {
            val intent= Intent(context, MyMainActivity::class.java).apply {
                flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        val notificationManager = NotificationManagerCompat
            .from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }
}