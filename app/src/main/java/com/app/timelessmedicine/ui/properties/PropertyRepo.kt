package com.app.timelessmedicine.ui.properties

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.bean.response.property.PropertyBean
import com.app.timelessmedicine.retrofit.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PropertyRepo {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var responseProperty: MutableLiveData<PropertyBean> = MutableLiveData()
    var errorResponse: MutableLiveData<Throwable> = MutableLiveData()

    companion object {
        private var instance: PropertyRepo? = null
        fun getInstance(): PropertyRepo {
            if (instance == null) {
                instance = PropertyRepo()
            }

            return instance!!
        }
    }

    @SuppressLint("CheckResult")
    fun hitProperty() {

        RetrofitUtil.createBaseApiService().getWholeProperty().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                {

                    responseProperty.value = it
                 //   setData(responseProperty )

                },
                {
                    errorResponse.value = it
                }
            )

    }

    fun getData(): MutableLiveData<PropertyBean> {
        return responseProperty
    }

}