package com.example.truthordare.notification

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.truthordare.SplashScreen
import java.util.concurrent.TimeUnit

class NotificationRepo {
    private val title = "We miss you \uD83D\uDE13"
    private val message = "Come back and play soon \uD83E\uDD3C"

        fun myNotification(mainActivity: SplashScreen){
            val myWorkRequest= PeriodicWorkRequest
                .Builder(WorkerNotification::class.java,15, TimeUnit.MINUTES)
                .setInputData(workDataOf("title" to title, "message" to message)).build()
            WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
                "periodicStockWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                myWorkRequest
            )
        }
    }
