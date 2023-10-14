package com.vnpay.demohackathon.utils

import android.app.Activity
import com.vnpay.demohackathon.data.Photo

class Loader(
    private val activity: Activity,
    private val photo: Photo,
): Runnable {

    override fun run() {
        val bitmap = Utils.g().getBitmap(photo.url, photo.reqWidth, photo.reqHeight)
        if (bitmap != null) {
            MemoryCache.put(photo.url, bitmap)
            activity.runOnUiThread {
                photo.imageView.setImageBitmap(bitmap)
            }
        }
    }
}