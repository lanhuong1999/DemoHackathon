package com.vnpay.demohackathon.data

import android.widget.ImageView

data class Photo(
    var url: String? = null,
    var reqWidth: Int? = null,
    var reqHeight: Int? = null,
    var imageView: ImageView? = null
)
