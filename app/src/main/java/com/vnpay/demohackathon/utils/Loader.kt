package com.vnpay.demohackathon.utils

import android.app.Activity
import android.content.Context
import com.vnpay.demohackathon.data.Photo

class Loader(
    private val context: Context,
    private val photo: Photo,
): Runnable {

    override fun run() {
        val bitmap = Utils.g().getBitmap(photo.url, photo.reqWidth, photo.reqHeight)
        if (bitmap != null) {
            MemoryCache.put(photo.url, bitmap)
            (context as Activity).runOnUiThread {
                photo.imageView.setImageBitmap(bitmap)
            }
        }
    }
}