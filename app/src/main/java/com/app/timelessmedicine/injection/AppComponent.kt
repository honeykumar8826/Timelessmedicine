package com.app.timelessmedicine.injection

import android.app.Application
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.ui.homepage.HomeViewModel
import com.app.timelessmedicine.ui.oilsandblends.OilBlendsRepo
import com.app.timelessmedicine.ui.profileviewedit.ProfileViewEditViewModel
import com.app.timelessmedicine.ui.selectcountry.SelectCountryActivity
import com.app.timelessmedicine.ui.selectlanguage.SelectLanguageActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    /* fun inject(app: RimoApplication)
     fun inject(app: NearMeViewModel)
     fun inject(app: LoginViewModel)
     fun inject(app: CompanionDetailViewModel)
     fun inject(app: EditRouteViewModel)
     fun inject(app: ScheduleRideViewModel)*/

    fun inject(app: MyApplication)
    fun inject(app: HomeViewModel)
    fun inject(app: ProfileViewEditViewModel)
    fun inject(app: SelectCountryActivity)
    fun inject(app: SelectLanguageActivity)
    fun inject(app: OilBlendsRepo)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application):Builder
        fun appModule(appModule: AppModule): Builder
        fun build():AppComponent
    }


}