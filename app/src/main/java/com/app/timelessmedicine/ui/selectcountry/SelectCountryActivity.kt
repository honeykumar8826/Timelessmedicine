package com.app.timelessmedicine.ui.selectcountry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.ActivitySelectCountryBinding
import com.app.timelessmedicine.ui.base.BaseActivity
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.utils.SharedPreferenceUtil
import javax.inject.Inject

class SelectCountryActivity : BaseActivity<ActivitySelectCountryBinding, SelectCountryViewModel>() {

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    private lateinit var selectCountryViewModel:SelectCountryViewModel
    private lateinit var activitySelectCountryBinding:ActivitySelectCountryBinding


    override val layoutId: Int
        get() = R.layout.activity_select_country


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        selectCountryViewModel = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)
        activitySelectCountryBinding = viewDataBinding!!
        viewModel = selectCountryViewModel
        (application as MyApplication).getAppComponent().inject(this)
        init()
    }

    fun init(){
        activitySelectCountryBinding.btnSelectCountry.setOnClickListener(myClickListener)
        activitySelectCountryBinding.tvCountryCode.setOnCountryChangeListener {
            activitySelectCountryBinding.btnSelectCountry.text =
                activitySelectCountryBinding.tvCountryCode.selectedCountryName
            sharedPreferenceUtil.country = activitySelectCountryBinding.tvCountryCode.selectedCountryName
            Handler().postDelayed(Runnable {
               // SharedPreferenceUtil.getInstance(applicationContext).isLoggedIn = true
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }, 200)
        }
    }

    private val myClickListener = View.OnClickListener{v->
        when(v.id){
            R.id.btnSelectCountry -> {
                activitySelectCountryBinding.tvCountryCode.launchCountrySelectionDialog()
            }
        }
    }
}
