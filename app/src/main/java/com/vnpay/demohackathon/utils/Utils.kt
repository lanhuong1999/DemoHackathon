package com.vnpay.demohackathon.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.vnpay.demohackathon.data.Photo
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.Collections
import java.util.WeakHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Utils private constructor() {

    companion object {
        private var instance: Utils? = null
        const val NUM_THREAD = 4
        val executorService: ExecutorService = Executors.newFixedThreadPool(NUM_THREAD)
        val views = Collections.synchronizedMap(WeakHashMap<ImageView, String>())

        @JvmStatic
        fun g(): Utils {
            if (instance == null) instance = Utils()
            return instance!!
        }
    }

    //viết các hàm xử lý dùng ở nhiều nơi


    fun getBitmapFromUrl(url: String): Bitmap {
        val url1 = URL(url)
        return BitmapFactory.decodeStream(url1.openConnection().getInputStream())
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun getBitmap(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        val file = File(url)
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        val stream = FileInputStream(file)
        BitmapFactory.decodeStream(stream, null, o)
        stream.close()
        val size = calculateInSampleSize(o, reqWidth, reqHeight)
        val o2 =  BitmapFactory.Options()
        o2.inSampleSize = size
        val inputStream = FileInputStream(file)
        val bitmap = BitmapFactory.decodeStream(inputStream, null, o2)
        inputStream.close()
        return bitmap
    }

    fun loadImage(context: Context, photo: Photo) {
        val bitmap = MemoryCache.get(photo.url ?: "")
       if (bitmap != null) {
           photo.imageView?.setImageBitmap(bitmap)
       }
       else {
           queuePhoto(context, photo)
       }
    }

    private fun queuePhoto(context: Context, photo: Photo) {
        val loader = Loader(context, photo)
        executorService.submit(loader)
    }
}