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

    companion object {
        const val PAYLOAD_SELECT = "PAYLOAD_SELECT"
    }

    var clickedItem: ((String?) -> Unit)? = null
    var longClickedItem: ((Any?) -> Unit)? = null
    private val isShowCheck = false
    private val countCheck = 0
    private val listSelect = mutableListOf<ImageSelectVHData>()
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

    inner class DisplayImageViewHolder(itemView: View) : BaseRclvVH<Any>(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.ivImage)
        private val icCheck: ImageView = itemView.findViewById(R.id.ivCheck)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position > -1) {
                    clickedItem?.invoke((mDataSet[position] as ImageSelectVHData).realData)
                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position > -1) {
//                    longClickedItem?.invoke(mDataSet[position])
                    val item = mDataSet[position] as? ImageSelectVHData
                    if (item != null) {
                        if (!item.isCheck) {
                            item.isCheck = true
                            listSelect.add(item)
                            notifyItemChanged(adapterPosition, PAYLOAD_SELECT)
                        }

                    }
                }
                true
            }
        }

        override fun onBind(data: Any) {
            if (data is ImageSelectVHData) {
                val photo = Photo(data.realData ?: "", 500, 500, image)
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

        override fun onBind(data: Any, payload: List<Any>) {
            if (payload.isEmpty()){
                super.onBind(data, payload)
            }else{
                payload.forEach {
                    if (it is Int && it == PAYLOAD_SELECT) {

                    }
                }
            }
        }


    }

    class ImageSelectVHData(data: String) : BaseVHData<String>(data) {
        var isCheck: Boolean = false
    }
}