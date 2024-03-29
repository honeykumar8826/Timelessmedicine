package com.app.timelessmedicine.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.app.timelessmedicine.R
import com.google.android.material.snackbar.Snackbar

object SnackbarUtils {
    @Suppress("unused")
    val TAG = SnackbarUtils::class.simpleName

    fun displaySnackbar(view: View?, message: String?, duration: Int = Snackbar.LENGTH_SHORT) {
        if (view == null) return
        if (message == null) {
            somethingWentWrong(view)
            return
        }

        val snackbar = Snackbar.make(view, message, duration)
        val tv = snackbar.view.findViewById(R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        snackbar.show()
    }


    fun somethingWentWrong(view: View?) {
        if (view == null) return
        displaySnackbar(view, view.context.getString(R.string.error_something_went_wrong_please_retry))
    }
/*
    fun displayError(view: View?, socketTimeoutException: SocketTimeoutException) {
        if (view == null) return
        displaySnackbar(view, view.context.getString(R.string.error_connection_please_check_internet))
    }

    fun displayError(view: View?, connectionException: ConnectException) {
        if (view == null) return
        displaySnackbar(view, view.context.getString(R.string.error_connection_please_check_internet))
    }

    fun displayError(view: View?, exception: HttpException) {
        Log.i(TAG, "displayError()")
        try {
            GsonUtil.mGsonInstance = Gson()
            val errorBody = GsonUtil.mGsonInstance!!.fromJson(exception.response().errorBody()?.charStream(), ErrorBean::class.java)
            SnackbarUtils.displaySnackbar(view, errorBody.message)
        } catch (e: Exception) {
            somethingWentWrong(view)
        }
    }
*/
}