package com.example.getusagestats

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class GetUsageStats(val context: Context) {
    private val TAG = "GetUsageStats"

    fun getUsageString(): String{
        Log.i(TAG,"Accessed GetUsageStatsClass")
        return showAppUsageStats(getAppUsageStats())
    }

    private fun getAppUsageStats(): MutableList<UsageStats> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR_OF_DAY, -25)
        // 取得する時間

        // queryUsageStats(取得する時間の単位, 取得する時間の始まり、取得する時間の終わり)
        // 取得する時間の単位 : 日単位(INTERVAL_DAILY)、週単位(INTERVAL_WEEKLY)、月単位(INTERVAL_MONTHLY)、
        //                    年単位(INTERVAL_YEARLY)、自動選択(INTERVAL_BEST)がある
        //
        // 取得する時間の始まり : 取得したいデータの時間帯のスタート地点。今回は、その日の午前0時。
        // 取得する時間の終わり : 取得したいデータの時間帯の終わり。今回は、現在時刻。

        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager // usageStatsManagerのオブジェクトの取得
        //val queryMap = usageStatsManager.queryAndAggregateUsageStats(cal.timeInMillis,System.currentTimeMillis())
        
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )// インターバルなど決定
    }

    private fun showAppUsageStats(usageStats: MutableList<UsageStats>): String {
        var str =""

        usageStats.sortWith(Comparator { right, left ->
            compareValues(right.lastTimeUsed, left.lastTimeUsed)
        })
        usageStats.forEach { it ->
            if (it.totalTimeInForeground.toInt() != 0) {
                val date = Date(it.lastTimeUsed)
                val eDate = Date(it.firstTimeStamp)
                val format = SimpleDateFormat("yyyy.MM.dd, E, HH:mm")
                str += "${it.packageName},${format.format(date)},${it.totalTimeInForeground},${format.format(eDate)}\n"
                //Log.d( ContentValues.TAG, "packageName: ${it.packageName}, lastTimeUsed: ${Date(it.lastTimeUsed)}" + "totalTimeInForeground: ${it.totalTimeInForeground}")
            }
        }
        return str
    }
}