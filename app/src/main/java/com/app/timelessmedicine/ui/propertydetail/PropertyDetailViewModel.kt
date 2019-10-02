package com.app.timelessmedicine.ui.propertydetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.bean.response.propertydetail.SinglePropertyBean
import com.app.timelessmedicine.retrofit.RetrofitUtil
import com.app.timelessmedicine.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PropertyDetailViewModel : BaseViewModel() {

//    var propertyDetailRepo: PropertyDetailRepo= PropertyDetailRepo.getInstance()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var responsePropertyDetail = MutableLiveData<SinglePropertyBean>()
    var errorResponse: MutableLiveData<Throwable> = MutableLiveData()

//    companion object {
//        private var instance: PropertyDetailRepo? = null
//        fun getInstance(): PropertyDetailRepo {
//            if (instance == null) {
//                instance = PropertyDetailRepo()
//            }
//
//            return instance!!
//        }
//    }

    @SuppressLint("CheckResult")
    fun hitPropertyDetail(name: String) {

        RetrofitUtil.createBaseApiService().getSingleProperty(name, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                {

                    responsePropertyDetail.value = it
                    //   setData(responseProperty )

                },
                {
                    errorResponse.value = it
                }
            )

    }

//    fun getPropertyData(name:String) {
//        propertyDetailRepo.hitPropertyDetail(name)
//        // return propertyRepo.getData()
//    }
}