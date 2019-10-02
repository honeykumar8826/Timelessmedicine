package com.app.timelessmedicine.ui.profileviewedit

import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.bean.response.ResponseProfile
import com.app.timelessmedicine.database.UserDataDao
import com.app.timelessmedicine.retrofit.ApiInterface
import com.app.timelessmedicine.ui.base.BaseViewModel
import com.app.timelessmedicine.utils.CommonUtils
import com.app.timelessmedicine.utils.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileViewEditViewModel(private val userDao:UserDataDao):BaseViewModel() {

    @Inject
    lateinit var apiInterface:ApiInterface

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    var isLoading:MutableLiveData<Boolean> = MutableLiveData()
    var errorResponse:MutableLiveData<Throwable> = MutableLiveData()
    var response:MutableLiveData<ResponseProfile> = MutableLiveData()
    var responseAdd:MutableLiveData<ResponseProfile> = MutableLiveData()


    fun addAccount(
        first_name:String,
        last_name:String,
        email_id:String,
        profile_image:MultipartBody.Part?)
    {

        mCompositeDisposable.add(apiInterface.addAccount(
            first_name = CommonUtils.createPartFromString(first_name),
            last_name = CommonUtils.createPartFromString(last_name),
            email_id = CommonUtils.createPartFromString(email_id),
            is_country = CommonUtils.createPartFromString(sharedPreferenceUtil.country),
            is_language = CommonUtils.createPartFromString(sharedPreferenceUtil.language),
            profile_image = profile_image,
            device_type = CommonUtils.createPartFromString("0")
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{isLoading.value = true}
            .doOnTerminate {isLoading.value = false}
            .subscribe(
                {
                    sharedPreferenceUtil.accessToken = it.response.access_token
                    userDao.insertAll(it.response)
                    responseAdd.value = it
                },
                {
                    errorResponse.postValue(it)
                }
            )
        )

    }


    fun editProfile(
        first_name:String,
        last_name:String,
        profile_image:MultipartBody.Part?){

        mCompositeDisposable.add(apiInterface.editProfile(
            access_token = sharedPreferenceUtil.accessToken,
            first_name = CommonUtils.createPartFromString(first_name),
            last_name = CommonUtils.createPartFromString(last_name),
            profile_image = profile_image
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{isLoading.value = true}
            .doOnTerminate {isLoading.value = false}
            .subscribe(
                {
                    it.response.id = 1
                    userDao.updateUser(it.response)
                    response.value = it
                },
                {
                    errorResponse.postValue(it)
                }
            )
        )
    }


    fun injectDagger(application: MyApplication){
        application.getAppComponent().inject(this)
    }

}