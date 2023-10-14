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
            if(Utils.views[photo.imageView] == photo.url) return
            val bitmap = Utils.g().getBitmap(photo.url ?: "", photo.reqWidth ?: 0, photo.reqHeight ?: 0)
            if (bitmap != null) {
                MemoryCache.put(photo.url ?: "", bitmap)
                (context as Activity).runOnUiThread {
                    photo.imageView?.setImageBitmap(bitmap)
                }
            }
        }
    }
}