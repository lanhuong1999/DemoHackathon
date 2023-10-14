package com.vnpay.demohackathon.utils

import android.graphics.Bitmap
import java.util.Collections

object MemoryCache {
    private val myMap = Collections.synchronizedMap(mutableMapOf<String, Bitmap>())
    fun put(key: String, bm: Bitmap) {
        myMap[key] = bm
    }

    fun get(key: String): Bitmap? {
        if (!myMap.containsKey(key)) return null
        return myMap[key]
    }

    fun clear() {
        myMap.clear()
    }
}