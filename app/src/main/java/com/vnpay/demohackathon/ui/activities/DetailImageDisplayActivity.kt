package com.vnpay.demohackathon.ui.activities

import android.content.Context
import android.content.Intent
import com.vnpay.demohackathon.bases.BaseActivity
import com.vnpay.demohackathon.data.Photo
import com.vnpay.demohackathon.databinding.ActivityDetailImageDisplayBinding
import com.vnpay.demohackathon.utils.Utils

class DetailImageDisplayActivity: BaseActivity<ActivityDetailImageDisplayBinding>() {
    override val binding: ActivityDetailImageDisplayBinding by lazy { ActivityDetailImageDisplayBinding.inflate(layoutInflater) }

    companion object{
        const val DATA = "DATA"

        fun getIntent(context: Context, url: String?): Intent{
            val intent = Intent(context, DetailImageDisplayActivity::class.java)
            intent.putExtra(DATA, url)
            return intent
        }
    }
    override fun initView() {
        val url = intent.getStringExtra(DATA)
        val displayMetric = resources.displayMetrics
        Utils.g().loadImage(this, Photo(url, displayMetric.widthPixels, displayMetric.heightPixels, binding.ivDetailImage))
    }

    override fun initData() {

    }

    override fun initAction() {

    }
}