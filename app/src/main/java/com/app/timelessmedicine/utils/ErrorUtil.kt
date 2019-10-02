package com.app.timelessmedicine.utils
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.ErrorBean
import com.app.timelessmedicine.ui.login.LoginActivity
import com.app.timelessmedicine.utils.GsonUtil
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object ErrorUtil {
    val TAG = ErrorUtil::class.simpleName

    fun handlerGeneralError(context: Context?, view:View, throwable: Throwable) {
        Log.e(TAG, "Error: ${throwable.message}")
        throwable.printStackTrace()

        if (context == null) return

        when (throwable) {
            //For Display Toast

            is ConnectException -> SnackbarUtils.displaySnackbar(view, context.getString(R.string.network_error))
            is SocketTimeoutException -> SnackbarUtils.displaySnackbar(view, context.getString(R.string.connection_lost_error))
            is UnknownHostException -> SnackbarUtils.displaySnackbar(view, context.getString(R.string.server_error))
            is InternalError -> SnackbarUtils.displaySnackbar(view, context.getString(R.string.server_error))

            is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            //Logout
                            forceLogout(context)
                        }
                        403 -> {

                            displayError(context, view, throwable)


                        }
                        else -> {

                            displayError(context, view, throwable)
                        }
                    }
                } catch (exception: Exception) {

                }
            }
            //For Display SnackBar
            /*is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            SnackbarUtils.displayError(view, throwable)
                            //logout(context)
                        }
                        else -> {
                            SnackbarUtils.displayError(view, throwable)
                        }
                    }
                } catch (exception: Exception) {
                    SnackbarUtils.somethingWentWrong(view)
                    exception.printStackTrace()
                }
            }
            is ConnectException -> SnackbarUtils.displayError(view, throwable)
            is SocketTimeoutException -> SnackbarUtils.displayError(view, throwable)
            else -> SnackbarUtils.somethingWentWrong(view)
       */

        }
    }



    /**
     * Perform logout for both the success and error case (force logout)
     */
    private fun forceLogout(context: Context) {
        SharedPreferenceUtil.getInstance(context).deletePreferences()
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun displayError(context: Context, view: View, exception: HttpException) {
        Log.i(TAG, "displayError()")
        try {
            GsonUtil.mGsonInstance = Gson()
            val errorBody = GsonUtil.mGsonInstance!!.fromJson(exception.response().errorBody()?.charStream(), ErrorBean::class.java)
            SnackbarUtils.displaySnackbar(view, errorBody.message)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.error_something_went_wrong_please_retry), Toast.LENGTH_SHORT).show()
        }
    }

}