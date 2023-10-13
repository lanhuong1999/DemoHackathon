package com.vnpay.demohackathon.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vnpay.demohackathon.utils.extensions.setSafeOnClickListener

class BaseRclvVH<T>(itemView: View) : ViewHolder(itemView), IOnBind<T> {

    init {
        onInitView(itemView)
    }

    override fun onInitView(itemView: View) {

    }

    override fun onBind(data: T, payload: List<Any>) {

    }

    override fun onBind(data: T) {

    }

    fun clickOn(view: View, listener: View.OnClickListener?) {
        if (listener != null) {
            view.setSafeOnClickListener {
                if (adapterPosition > -1) {
                    listener.onClick(view)
                }
            }
        }
    }

    fun longClickOn(view: View, listener: View.OnLongClickListener?) {
        if (listener != null) {
            view.setOnLongClickListener {
                if (adapterPosition > -1) {
                    return@setOnLongClickListener listener.onLongClick(view)
                }
                true
            }
        }
    }
}