package com.vnpay.demohackathon.ui.adapters

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreRecyclerView: RecyclerView {
    private var reverse = false
    private var mAdapter: Adapter<*>? = null
    private lateinit var mLayoutManager: LinearLayoutManager
    private var isLoading = false
    private var mLoadDataListener: IOnLoadMoreRecyclerViewListener? = null

    constructor(context: Context) : super(context) {
        initLayoutManager(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
        initLayoutManager(context)
        initScrollListener()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
        initLayoutManager(context)
        initScrollListener()
    }

    fun setLoadDataListener(listener: IOnLoadMoreRecyclerViewListener) {
        mLoadDataListener = listener
    }

    private fun init(attrs: AttributeSet?) {
//        if (attrs == null) {
//            return
//        }
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadMoreRecyclerView)
//        reverse = typedArray.getBoolean(R.styleable.LoadMoreRecyclerView_wrap_rclv_reverse, false)
//        typedArray.recycle()
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter is LoadMoreAdapter) {
            adapter.onAddItemListener = object : LoadMoreAdapter.IOnAddItemListener {
                override fun onAdded(startIndex: Int, count: Int) {
                    isLoading = false
                    if (count == 0) {
                        mLoadDataListener?.onLoadEmptyData(true)
                    } else {
                        post {
                            mLoadDataListener?.onLoadEmptyData(false)
                            // 1 for down and -1 for up
                            var direction = 1
                            if (reverse) {
                                direction = -1
                            }
                            if (!canScrollVertically(direction)
                                && adapter.isLoadMoreSupport()
                            ) {
                                isLoading = true
                                mLoadDataListener?.onLoadData()
                            }
                        }
                    }
                }
            }
        }
        mAdapter = adapter
        super.setAdapter(mAdapter)
    }


    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is LinearLayoutManager) {
            reverse = layout.reverseLayout
            mLayoutManager = layout
            super.setLayoutManager(layout)
            return
        }
        throw IllegalArgumentException("LayoutManager must be LinearLayoutManager")
    }

    private fun initLayoutManager(context: Context) {
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, reverse)
        layoutManager = mLayoutManager
    }

    private fun initScrollListener() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mAdapter !is LoadMoreAdapter ||
                    !(mAdapter as ILoadMoreAdapter).isLoadMoreSupport()
                ) {
                    return
                }

                if (mLoadDataListener == null) {
                    return
                }

                var checkRotation = dy > 0
                if (reverse) {
                    checkRotation = dy < 0
                }
                if (checkRotation && !isLoading) {
                    val visibleItemCount: Int = mLayoutManager.childCount
                    val totalItemCount: Int = mLayoutManager.itemCount
                    val pastVisibleItems: Int = mLayoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        isLoading = true
                        mLoadDataListener?.onLoadData()
                    }
                }
            }
        })
    }

    interface IOnLoadMoreRecyclerViewListener {
        fun onLoadData()
        fun onLoadEmptyData(isEmpty: Boolean)
    }
}