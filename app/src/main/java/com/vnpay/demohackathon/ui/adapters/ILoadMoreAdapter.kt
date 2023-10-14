package com.vnpay.demohackathon.ui.adapters

interface ILoadMoreAdapter {
    fun showLoadMore(isShown: Boolean)
    fun isLoadMoreSupport(): Boolean
    fun enableLoadMore(isLoadMore: Boolean)
}