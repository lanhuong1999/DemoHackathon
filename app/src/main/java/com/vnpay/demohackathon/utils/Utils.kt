package com.vnpay.demohackathon.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.URL

class Utils private constructor() {
    companion object {
        private var instance: Utils? = null

        @JvmStatic
        fun g(): Utils {
            if (instance == null) instance = Utils()
            return instance!!
        }
    }

    private var gson: Gson? = null

    //viết các hàm xử lý dùng ở nhiều nơi

    fun provideGson(): Gson {
        if (gson == null) gson = GsonBuilder().disableHtmlEscaping().create()
        return gson!!
    }

    fun getBitmapFromUrl(url: String): Bitmap {
        val url1 = URL(url)
        return BitmapFactory.decodeStream(url1.openConnection().getInputStream())
    }
}