package com.app.timelessmedicine.ui.oilsandblends

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.bean.response.oilblends.OilBlendBean
import com.app.timelessmedicine.retrofit.ApiInterface
import com.app.timelessmedicine.retrofit.RetrofitUtil
import com.app.timelessmedicine.ui.base.MyBaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OilBlendsRepo
 {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var responseOilAndBlend: MutableLiveData<OilBlendBean> = MutableLiveData()
    var errorResponse: MutableLiveData<Throwable> = MutableLiveData()

        companion object
        {
            private var instance: OilBlendsRepo? = null
            fun getInstance(): OilBlendsRepo {
                if (instance == null) {
                    instance = OilBlendsRepo()
                }
                return instance!!
            }
        }

    @SuppressLint("CheckResult")
    fun hitOilAndBlend() {

        RetrofitUtil.createBaseApiService().getAllOilAndBlend().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                {

                    responseOilAndBlend.value = it
                },
                {
                    errorResponse.value = it
                }
            )

    }

    fun getOilAndBlendData(): MutableLiveData<OilBlendBean> {
        hitOilAndBlend()
        return responseOilAndBlend
    }
}