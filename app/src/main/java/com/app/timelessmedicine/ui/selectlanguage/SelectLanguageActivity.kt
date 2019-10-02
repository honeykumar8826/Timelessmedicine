package com.app.timelessmedicine.ui.selectlanguage

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.ActivitySelectLanguageBinding
import com.app.timelessmedicine.ui.base.BaseActivity
import com.app.timelessmedicine.ui.selectcountry.SelectCountryActivity
import com.app.timelessmedicine.utils.SharedPreferenceUtil
import javax.inject.Inject

class SelectLanguageActivity : BaseActivity<ActivitySelectLanguageBinding, SelectLanguageViewModel>() {

    private lateinit var selectLangViewModel:SelectLanguageViewModel

    private lateinit var activitySelectLanguageBinding:ActivitySelectLanguageBinding
    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil


/*

    override val viewModel: SelectLanguageViewModel
        get() = selectLangViewModel

*/

    override val layoutId: Int
        get() = R.layout.activity_select_language



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).getAppComponent().inject(this)


       // window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        } */

        selectLangViewModel =  ViewModelProviders.of(this).get(SelectLanguageViewModel::class.java)
        activitySelectLanguageBinding = viewDataBinding!!
        viewModel = selectLangViewModel
        init()
        intiRadioButtons()
    }

    fun init(){
        activitySelectLanguageBinding.langView.visibility = View.GONE
        activitySelectLanguageBinding.titleView.setOnClickListener {
            if(activitySelectLanguageBinding.langView.visibility == View.VISIBLE) {
                activitySelectLanguageBinding.langView.visibility = View.GONE
            } else {
                activitySelectLanguageBinding.langView.visibility = View.VISIBLE
            }
        }
    }

    fun intiRadioButtons(){
        activitySelectLanguageBinding.rbEng.setOnCheckedChangeListener { compoundButton, b ->
            sharedPreferenceUtil.language = activitySelectLanguageBinding.rbEng.text.toString()
            startActivity(Intent(this, SelectCountryActivity::class.java))
        }
        activitySelectLanguageBinding.rbHung.setOnCheckedChangeListener { compoundButton, b ->
            sharedPreferenceUtil.language = activitySelectLanguageBinding.rbHung.text.toString()
            startActivity(Intent(this, SelectCountryActivity::class.java))

        }
        activitySelectLanguageBinding.rbCzch.setOnCheckedChangeListener { compoundButton, b ->
            sharedPreferenceUtil.language = activitySelectLanguageBinding.rbCzch.text.toString()
            startActivity(Intent(this, SelectCountryActivity::class.java))

        }
        activitySelectLanguageBinding.rbSlov.setOnCheckedChangeListener { compoundButton, b ->
            sharedPreferenceUtil.language = activitySelectLanguageBinding.rbSlov.text.toString()
            startActivity(Intent(this, SelectCountryActivity::class.java))
        }
    }
}
