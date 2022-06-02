package com.example.getusagestats

import android.app.Application

class GetUsageStatsApp: Application() {
    private lateinit var instance : Application
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    fun getInstance(): Application{
        return instance
    }
}