package com.shadow.voicepublishing.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.shadow.voicepublishing.utils.loadImage


@BindingAdapter("loadImage")
fun loadImages(imageView: ImageView, resource: String) {
    imageView.loadImage(resource)
}




