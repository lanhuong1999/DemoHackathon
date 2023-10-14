package com.vnpay.demohackathon.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vnpay.demohackathon.bases.BaseActivity
import com.vnpay.demohackathon.data.GalleryRepo
import com.vnpay.demohackathon.databinding.ActivityMainBinding
import com.vnpay.demohackathon.ui.adapters.LoadMoreRecyclerView

class MainActivity() : BaseActivity<ActivityMainBinding>() {
    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var page = 0
    private var limit = 20
    private var isLoading: Boolean = false
    private var stillMore: Boolean = false
    private val displayImageAdapter: DisplayImageAdapter by lazy { DisplayImageAdapter() }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                setData()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES
                    ),
                    1000
                )
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData()
        }
    }

    override fun initView() {
        permissionResult.launch(Manifest.permission.READ_MEDIA_IMAGES)
    }

    override fun initData() {
    }

    private fun setData(){
        page = 0
        stillMore = true
        isLoading = false
        binding.rclDisplay.layoutManager = GridLayoutManager(this, 3)
        binding.rclDisplay.adapter = displayImageAdapter
        loadImage()
    }

    private fun loadImage() {
        val list = GalleryRepo.getListImages(this, limit, page)
        page++
        displayImageAdapter.enableLoadMore(true)
        displayImageAdapter.resetData(list)
        binding.rclDisplay.setLoadDataListener(object : LoadMoreRecyclerView.IOnLoadMoreRecyclerViewListener {
            override fun onLoadData() {
                val listNext = GalleryRepo.getListImages(this@MainActivity, limit, page)
                val isLastPage = listNext.isNullOrEmpty() || listNext.size < limit
                if (!listNext.isNullOrEmpty()) {
                    displayImageAdapter.addMoreItem(listNext, !isLastPage)
                    page++
                }
            }

            override fun onLoadEmptyData(isEmpty: Boolean) {
            }

        })

    }

    override fun initAction() {
        displayImageAdapter.clickedItem = {
            startActivity(DetailImageDisplayActivity.getIntent(this, it))
        }
    }

}