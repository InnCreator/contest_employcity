package com.inn_creator.contestemploycity

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("app:isFavorite")
fun goneUnless(view: ImageView, isFavorite: Boolean) {
    if (isFavorite){
        view.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_star))
    }else{
        view.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_star_border))
    }
}

@BindingAdapter("app:visibility")
fun visibilityView(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}