package com.example.homemap

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices

class MapUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val homeLat = 35.6895
        val homeLng = 139.6917
        val fused = LocationServices.getFusedLocationProviderClient(applicationContext)
        val loc = fused.lastLocation
        val location = loc.result ?: return Result.retry()

        val apiKey = BuildConfig.MAPS_API_KEY
        val bitmap = MapImageGenerator.getMap(location.latitude, location.longitude, homeLat, homeLng, apiKey)
            ?: return Result.retry()

        val ids = intentIds()
        for (id in ids) {
            MapWidgetProvider.updateWidget(applicationContext, AppWidgetManager.getInstance(applicationContext), id, bitmap)
        }
        return Result.success()
    }

    private fun intentIds(): IntArray {
        val prefs = applicationContext.getSharedPreferences("widgets", Context.MODE_PRIVATE)
        val set = prefs.getStringSet("ids", emptySet()) ?: emptySet()
        return set.mapNotNull { it.toIntOrNull() }.toIntArray()
    }
}
