package com.example.homemap

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MapWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        for (id in ids) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            views.setImageViewResource(R.id.image, R.drawable.ic_placeholder)
            manager.updateAppWidget(id, views)
        }
        saveIds(context, ids)
        val request = OneTimeWorkRequestBuilder<MapUpdateWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }

    companion object {
        fun updateWidget(context: Context, manager: AppWidgetManager, id: Int, bitmap: Bitmap) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            views.setImageViewBitmap(R.id.image, bitmap)
            manager.updateAppWidget(id, views)
        }

        private fun saveIds(context: Context, ids: IntArray) {
            val prefs = context.getSharedPreferences("widgets", Context.MODE_PRIVATE)
            val set = ids.map { it.toString() }.toSet()
            prefs.edit().putStringSet("ids", set).apply()
        }
    }
}
