package com.app.timelessmedicine.utils

import com.google.android.gms.maps.model.MarkerOptions
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.MapView
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.graphics.drawable.Drawable




class BindingUtils {

    companion object {
        @BindingAdapter("initMap")
        fun initMap(mapView: MapView?, latLng: LatLng) {

            if (mapView != null) {
                mapView.onCreate(Bundle())
                mapView.getMapAsync { googleMap ->
                    googleMap.addMarker(MarkerOptions().position(latLng).title(""))
                }
            }
        }

        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                .load(ApiConstant.IMAGE_BASE_URL+""+url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }

        @BindingAdapter("imageResource")
        fun setImageDrawable(view: ImageView, drawable: Drawable) {
            view.setImageDrawable(drawable)
        }

        @BindingAdapter("android:src")
        fun setImageResource(view: ImageView, resource: Int) {
            view.setImageResource(resource)
        }

    }
}