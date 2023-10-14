package com.vnpay.demohackathon.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.vnpay.demohackathon.data.Photo
import java.util.Collections
import java.util.WeakHashMap


class Loader(
    private val context: Context,
    private val photo: Photo,
): Runnable {

    override fun run() {
        if (photo.url != null && photo.reqHeight != null && photo.reqWidth != null) {
            if (check(photo)) return
            val bitmap = Utils.g().getBitmap(photo.url ?: "", photo.reqWidth ?: 0, photo.reqHeight ?: 0)
            if (bitmap != null) {
                MemoryCache.put(photo.url ?: "", bitmap)
                (context as Activity).runOnUiThread {
                    if (check(photo)) return@runOnUiThread
                    photo.imageView?.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun check(photo: Photo): Boolean {
        val str = Utils.views[photo.imageView]
        return (str == null || str != photo.url)
    }
}