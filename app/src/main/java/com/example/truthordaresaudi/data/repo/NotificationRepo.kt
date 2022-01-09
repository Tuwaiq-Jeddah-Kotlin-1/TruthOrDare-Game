package com.example.truthordaresaudi.data.repo

import android.app.Activity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.truthordaresaudi.MESSAGE
import com.example.truthordaresaudi.TITLE
import com.example.truthordaresaudi.notification.WorkerNotification
import com.example.truthordaresaudi.ui.MyMainActivity
import com.example.truthordaresaudi.ui.SplashScreen
import java.util.concurrent.TimeUnit

class NotificationRepo {

        fun myNotification(mainActivity: Activity){
            val myPeriodicWorker= PeriodicWorkRequest
                .Builder(WorkerNotification::class.java,2, TimeUnit.DAYS)
                .setInputData(workDataOf("title" to TITLE, "message" to MESSAGE)).build()
            WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
                "periodicGameWorker",
                ExistingPeriodicWorkPolicy.REPLACE,
                myPeriodicWorker
            )
        }
    }
