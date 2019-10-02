package com.app.timelessmedicine.utils

import android.content.Context
import okhttp3.RequestBody
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE


object CommonUtils {

    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, descriptionString
        )
    }

    fun isGPSEnabled(context: Context):Boolean{
        val manager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
        return manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}