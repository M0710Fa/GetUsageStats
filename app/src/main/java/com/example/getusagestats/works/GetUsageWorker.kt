package com.example.getusagestats.works

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.getusagestats.GetUsageStats
import com.example.getusagestats.Repository
import java.lang.Error
import java.lang.Exception


class GetUsageWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "com.example.android.getusagestats.work.GetUsageWorker"
    }

    private val fileName = "data.txt"

    private val context = appContext
    val repository = Repository(context)

    override suspend fun doWork(): Result {
        try {
            Log.i("Worker","Work request for sync is run")
            val past = repository.readFile(fileName)
            val current = GetUsageStats(context).getUsageString()
            val usageData = past + current
            if (usageData != null) {
                repository.saveFile(fileName,usageData)
            }
        }catch (e:Exception){

        }
        return Result.success()
    }
}