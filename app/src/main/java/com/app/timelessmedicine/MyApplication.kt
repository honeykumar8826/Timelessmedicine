package com.app.timelessmedicine

import android.app.Application
import com.app.timelessmedicine.injection.AppComponent
import com.app.timelessmedicine.injection.AppModule
import com.app.timelessmedicine.injection.DaggerAppComponent

class MyApplication:Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent =  DaggerAppComponent
            .builder()
            .application(this)
            .appModule(AppModule())
            .build()

        appComponent?.inject(this)
    }

    fun getAppComponent():AppComponent{
                return appComponent!!
    }



}