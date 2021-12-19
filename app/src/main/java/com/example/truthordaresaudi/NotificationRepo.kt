package com.example.truthordaresaudi

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class NotificationRepo {
        fun myNotification(mainActivity: SplashScreen){
            val myWorkRequest= PeriodicWorkRequest
                .Builder(WorkerNotification::class.java,15, TimeUnit.MINUTES)
                .setInputData(workDataOf(
                    "title" to "It's been a while without some playing \uD83D\uDE13",
                    "message" to "Come back and challenge your friends with new dare options \uD83E\uDD3C")
                )
                .build()
            WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
                "periodicStockWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                myWorkRequest
            )
        }
    }
