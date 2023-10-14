package com.vnpay.demohackathon.ui.activities

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.vnpay.demohackathon.R
import com.vnpay.demohackathon.data.Photo
import com.vnpay.demohackathon.ui.adapters.BaseRclvVH
import com.vnpay.demohackathon.ui.adapters.BaseVHData
import com.vnpay.demohackathon.ui.adapters.LoadMoreAdapter
import com.vnpay.demohackathon.utils.Utils
import com.vnpay.demohackathon.utils.extensions.setSafeOnClickListener

class DisplayImageAdapter : LoadMoreAdapter() {

    var clickedItem: ((Any?) -> Unit)? = null
    var longClickedItem: ((Any?) -> Unit)? = null
    private val isShowCheck = false
    private val countCheck = 0
    override fun getLayoutResByViewType(viewType: Int): Int {
        return R.layout.display_image_item
    }

    override fun onCreateVHInfo(itemView: View, viewType: Int): BaseRclvVH<*> {
        return DisplayImageViewHolder(itemView)
    }

    fun resetData(data: List<String>) {
        val itemList = mutableListOf<ImageSelectVHData>()
        data.forEachIndexed { index, item ->
            itemList.add(ImageSelectVHData(item).apply {
                isCheck = false
            })
        }
        reset(itemList)
    }

    inner class DisplayImageViewHolder(itemView: View) : BaseRclvVH<ImageSelectVHData>(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.ivImage)
        private val icCheck: ImageView = itemView.findViewById(R.id.ivCheck)

        init {
            itemView.setSafeOnClickListener {
                val position = adapterPosition
                if (position > -1) {
                    clickedItem?.invoke(mDataSet[position])
                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position > -1) {
                    longClickedItem?.invoke(mDataSet[position])
                }
                true
            }
        }

        override fun onBind(data: ImageSelectVHData) {
            val photo = Photo(data.realData ?: "", 100, 100, image)
            Utils.g().loadImage(itemView.context, photo)
            if (isShowCheck && !data.isCheck) {
                icCheck.isVisible = true
                icCheck.setImageResource(R.drawable.ic_radio_button_unselected)
            } else if (isShowCheck && data.isCheck) {
                icCheck.isVisible = true
                icCheck.setImageResource(R.drawable.ic_radio_button_check)
            } else {
                icCheck.isVisible = false
            }
        }

    }

    class ImageSelectVHData(data: String) : BaseVHData<String>(data) {
        var isCheck: Boolean = false
    }
}