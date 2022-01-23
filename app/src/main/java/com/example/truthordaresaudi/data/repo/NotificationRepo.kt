package com.example.truthordaresaudi.data.repo

import android.app.Activity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.truthordaresaudi.MESSAGE
import com.example.truthordaresaudi.TITLE
import com.example.truthordaresaudi.notification.WorkerNotification
import java.util.concurrent.TimeUnit

class NotificationRepo {

        fun myNotification(mainActivity: Activity){
            val myPeriodicWorker= PeriodicWorkRequest
                .Builder(WorkerNotification::class.java,15, TimeUnit.MINUTES)
                .setInputData(workDataOf("title" to TITLE, "message" to MESSAGE)).build()
            WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
                "periodicGameWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                myPeriodicWorker
            )
        }
    }
