package com.vnpay.demohackathon.utils.extensions

import android.os.SystemClock
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vnpay.demohackathon.utils.Utils

class SafeClickListener(
    private var defaultInterval: Int = 100,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(p0: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeClick(p0)
    }

}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        try {
            onSafeClick(it)
        } catch (e: Exception) {
            Log.wtf("EX", e)
        }
    }
}



inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

