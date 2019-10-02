package com.app.timelessmedicine.ui.oilandblenddetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.bean.response.blends.SingleBlendBean
import com.app.timelessmedicine.bean.response.oils.SingleOilBean
import com.app.timelessmedicine.retrofit.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OilBlendDetailRepo {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
//    var responseOil: MutableLiveData<SingleOilBean> = MutableLiveData()
//    var errorResponse: MutableLiveData<Throwable> = MutableLiveData()

    companion object
    {
        private var instance: OilBlendDetailRepo? = null
        fun getInstance(): OilBlendDetailRepo {
            if (instance == null) {
                instance = OilBlendDetailRepo()
            }
            return instance!!
        }
    }


    @SuppressLint("CheckResult")
    fun hitOil(oilName: String): MutableLiveData<SingleOilBean> {
        var responseOilData: MutableLiveData<SingleOilBean> = MutableLiveData()
        RetrofitUtil.createBaseApiService().oilDetailOnly(oilName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                {

                    responseOilData.value = it
                },
                {
                    //errorResponse.value = it
                }
            )
        return responseOilData

    }
    @SuppressLint("CheckResult")
    fun hitBlend(blendName: String): MutableLiveData<SingleBlendBean> {
        var responseBlendData: MutableLiveData<SingleBlendBean> = MutableLiveData()
        RetrofitUtil.createBaseApiService().blendDetailOnly(blendName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                {

                    responseBlendData.value = it
                },
                {
                    Log.i("a",""+it)
                    //errorResponse.value = it
                }
            )
        return responseBlendData

    }
}
