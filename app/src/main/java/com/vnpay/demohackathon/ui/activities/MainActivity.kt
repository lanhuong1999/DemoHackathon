package com.vnpay.demohackathon.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import com.vnpay.demohackathon.R
import com.vnpay.demohackathon.bases.BaseActivity
import com.vnpay.demohackathon.databinding.ActivityMainBinding

class MainActivity(): BaseActivity<ActivityMainBinding>() {
    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var page = 0
    private var limit = 20
    private var isLoading: Boolean = false
    private var stillMore: Boolean = false

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData()
        }
    }
    override fun initView() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                1000
            )
        } else {
            initData()
        }
    }

    override fun initData() {
        page = 0
        stillMore = true
        isLoading = false
    }

    override fun initAction() {

    }
}