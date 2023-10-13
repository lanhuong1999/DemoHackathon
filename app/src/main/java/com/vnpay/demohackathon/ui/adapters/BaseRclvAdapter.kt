package com.vnpay.demohackathon.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRclvAdapter : RecyclerView.Adapter<BaseRclvVH<Any?>>() {

    protected var mDataSet = mutableListOf<Any?>()

    abstract fun getLayoutResource(viewType: Int): Int
    abstract fun onCreateVH(itemView: View, viewType: Int): BaseRclvVH<*>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRclvVH<Any?> {
        val layout = getLayoutResource(viewType)
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return onCreateVH(v, viewType) as BaseRclvVH<Any?>
    }

    override fun getItemCount(): Int = mDataSet.size

    override fun onBindViewHolder(holder: BaseRclvVH<Any?>, position: Int) {
        holder.onBind(getItemDataAtPosition(position))
    }

    override fun onBindViewHolder(
        holder: BaseRclvVH<Any?>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(getItemDataAtPosition(position), payloads)
        }
    }

    fun getItemDataAtPosition(position: Int): Any {
        return mDataSet[position]!!
    }

    fun addItem(item: Any?) {
        mDataSet.add(item)
    }

    fun addItems(items: List<Any>?) {
        if (items.isNullOrEmpty()) {
            return
        }
        mDataSet.addAll(items)
    }

    fun addItemAndNotify(item: Any?) {
        mDataSet.add(item)
        notifyItemInserted(mDataSet.size - 1)
    }

    fun addItemAtIndex(index: Int, item: Any?) {
        mDataSet.add(index, item)
    }

    fun addItemIndexAndNotify(index: Int, item: Any?) {
        mDataSet.add(index, item)
        notifyItemInserted(index)
    }

    fun addItemsAtIndex(items: List<Any?>, index: Int) {
        if (items.isNullOrEmpty()) {
            return
        }
        mDataSet.addAll(index, items)
    }

    fun addItemsAndNotify(items: List<*>) {
        mDataSet.addAll(items)
        notifyItemRangeInserted(mDataSet.size, items.size)
    }

    fun removeItemAndNotify(index: Int) {
        mDataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    open fun reset(newItems: List<Any>?) {
        mDataSet.clear()
        addItems(newItems)
    }

    fun remove(index: Int) {
        mDataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clearData() {
        mDataSet.clear()
    }

}