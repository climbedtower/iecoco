package com.example.homemap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

object MapImageGenerator {
    fun getMap(startLat: Double, startLng: Double, homeLat: Double, homeLng: Double, apiKey: String): Bitmap? {
        val url = "https://maps.googleapis.com/maps/api/staticmap" +
                "?size=600x400" +
                "&markers=color:blue|label:S|$startLat,$startLng" +
                "&markers=color:red|label:H|$homeLat,$homeLng" +
                "&path=$startLat,$startLng|$homeLat,$homeLng" +
                "&key=$apiKey"
        return try {
            val connection = URL(url).openConnection()
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            null
        }
    }
}
