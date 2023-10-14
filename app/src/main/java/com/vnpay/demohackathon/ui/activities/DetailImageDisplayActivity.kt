package com.vnpay.demohackathon.ui.activities

import android.content.Context
import com.vnpay.demohackathon.bases.BaseActivity
import com.vnpay.demohackathon.databinding.ActivityDetailImageDisplayBinding

class DetailImageDisplayActivity: BaseActivity<ActivityDetailImageDisplayBinding>() {
    override val binding: ActivityDetailImageDisplayBinding by lazy { ActivityDetailImageDisplayBinding.inflate(layoutInflater) }

    companion object{
        const val DATA = "DATA"

        fun getIntent(context: Context, url: String){

        }
    }
    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initAction() {
        TODO("Not yet implemented")
    }
}