package com.example.getusagestats

import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.getusagestats.works.GetUsageWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class GetUsageStatsApp: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private lateinit var instance : Application

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    fun getInstance(): Application{
        return instance
    }

    private fun delayedInit(){

    }

/*    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()
        //val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS) .build()
        val request = PeriodicWorkRequestBuilder<GetUsageWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(baseContext).enqueueUniquePeriodicWork(
            GetUsageWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }*/
}