package com.app.timelessmedicine.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.app.timelessmedicine.retrofit.ApiInterface
import com.app.timelessmedicine.retrofit.RetrofitUtil

abstract class MyBaseActivity: AppCompatActivity() {


    val apiInterface: ApiInterface by lazy {
        RetrofitUtil.createBaseApiService()
    }

}