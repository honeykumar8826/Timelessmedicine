package com.app.timelessmedicine.ui.homepage

import android.content.Context
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.HomeBean
import com.app.timelessmedicine.injection.AppComponent
import com.app.timelessmedicine.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel:BaseViewModel() {

    var ctx: Context? = null

    fun injectDagger(appComponent: AppComponent){
        appComponent.inject(this)
    }

    private val lists = ArrayList<HomeBean>()

    val adapterHomePage: AdapterHomePage =
        AdapterHomePage()

    fun setData(ctx:Context){
        this.ctx = ctx
        getLists()
        adapterHomePage.updateHomePageList(lists, ctx)
    }

    fun getLists(){
        lists.clear()
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.condition),
            R.drawable.condition_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.symptoms),
            R.drawable.symptoms_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.remedies),
            R.drawable.remedies_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.body_systems),
            R.drawable.body_systems_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.oil_amp_blends),
            R.drawable.oils_and_blends_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.supplements),
            R.drawable.supplements_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.properties),
            R.drawable.properties_white))
        lists.add(HomeBean(""+ctx?.resources?.getString(R.string.me_amp_referral),
            R.drawable.me_andreferral))
    }

}