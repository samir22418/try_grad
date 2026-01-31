package com.example.try_grad

import android.app.Application

class SecurityScannerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize app-wide components here
        // Room database will be initialized lazily
    }
}