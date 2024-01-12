package com.shadow.voicepublishing.ui.dialogs

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.shadow.voicepublishing.databinding.DialogProgressBinding
import com.shadow.voicepublishing.utils.gone
import com.shadow.voicepublishing.utils.visible


class LoadingDialog (activity: Activity) {

    private var loadingDialogBuilder: AlertDialog.Builder
    private var binding: DialogProgressBinding
    private var loadingDialog: AlertDialog

    init {
        loadingDialogBuilder = AlertDialog.Builder(activity)
        binding = DialogProgressBinding.inflate(LayoutInflater.from(activity), null, false)
        loadingDialogBuilder.setView(binding.root)
        loadingDialogBuilder.setCancelable(false)
        loadingDialogBuilder.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return@setOnKeyListener false
            }
            return@setOnKeyListener true
        }
//        binding.llLoading.background = activity.getDrawable()
//            getDrawable(Color.WHITE, topLeft = 10.dp.toFloat(), topRight = 10.dp.toFloat(), bottomRight = 10.dp.toFloat(), bottomLeft = 10.dp.toFloat())
        loadingDialog = loadingDialogBuilder.create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setProgressColor(color: Int) {
        binding.progressBar.indeterminateTintList = ColorStateList.valueOf(color)
    }

    fun setProgressText(text: String) {
        binding.txtProgress.text = text
    }

    fun setProgressTextColor(color: Int) {
        binding.txtProgress.setTextColor(color)
    }
//
//    fun setDialogBackgroundColor(color: Int, radius: Int) {
//        binding.root.background = getDrawable(
//            color,
//            topLeft = radius.dp.toFloat(), topRight = radius.dp.toFloat(), bottomRight = radius.dp.toFloat(), bottomLeft = radius.dp.toFloat()
//        )
//    }

    fun show(msg: String = "") {
        if (msg.isNotEmpty()) binding.txtProgress.visible()
        else binding.txtProgress.gone()
        setProgressText(msg)
        loadingDialog.show()
    }

    fun dismiss() {
        loadingDialog.dismiss()
    }


}