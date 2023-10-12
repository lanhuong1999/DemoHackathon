package com.vnpay.demohackathon.utils

class Utils private constructor() {
    companion object {
        private var instance: Utils? = null

        @JvmStatic
        fun g(): Utils {
            if (instance == null) instance = Utils()
            return instance!!
        }
    }

    //viết các hàm xử lý dùng ở nhiều nơi
}