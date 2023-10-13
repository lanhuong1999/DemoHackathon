package com.vnpay.demohackathon.ui.adapters

open class BaseVHData<T>(data: T) {
    var type = 0
    var realData: T? = data
}