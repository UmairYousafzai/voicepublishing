package com.shadow.voicepublishing.utils

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.shadow.voicepublishing.R

fun ImageView.loadImage(url: String?) {
    val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
    circularProgressDrawable?.strokeWidth = 5f
    circularProgressDrawable?.centerRadius = 30f
    circularProgressDrawable?.start()
    context?.let {
        if (url.isNullOrEmpty().not()) {
            Glide.with(it)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
        } else {
            Glide.with(it)
                .load(R.drawable.voice_digital_logo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
        }
    }
}

fun Fragment.showSnackBar(message: String) {
    val snackbar = Snackbar
        .make(this.requireView(), message, Snackbar.LENGTH_LONG)
    snackbar.show()
}

fun showSnackBar(view: View, message: String) {
    val snackbar = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
    snackbar.show()
}
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
