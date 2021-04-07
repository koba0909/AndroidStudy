package com.example.kunny_exam.Common

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageViewUtils {

    fun setGlideImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    fun setGlideImage(
        view: ImageView,
        url: String?,
        placeHolder: Int
    ) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeHolder)
            return
        }
        Glide.with(view.context)
            .load(url)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(view)
    }

}