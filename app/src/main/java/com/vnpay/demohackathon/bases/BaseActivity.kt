package com.vnpay.demohackathon.bases

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.vnpay.demohackathon.R

abstract class BaseActivity<T: ViewBinding>: AppCompatActivity() {
    protected abstract val binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initData()
        initAction()
    }

    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initAction()

    protected fun createDialog(title: String, msg: String?, listener: OnDialogClickListener?): Dialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(msg)
            .setPositiveButton(getString(R.string.dialog_positive_button_name)) {
                    dialog, _ -> listener?.onPositiveBtnClick(dialog)
            }
            .setNegativeButton(getString(R.string.dialog_negative_button_name)) {
                    dialog, _ -> listener?.onNegativeBtnClick(dialog)
            }
        return builder.create()
    }

    protected fun showLoading() {

    }

    protected fun hideLoading() {

    }

    interface OnDialogClickListener {
        fun onPositiveBtnClick(dialog: DialogInterface)
        fun onNegativeBtnClick(dialog: DialogInterface)
    }
}