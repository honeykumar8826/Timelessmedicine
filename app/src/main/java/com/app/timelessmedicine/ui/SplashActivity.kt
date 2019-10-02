package com.app.timelessmedicine.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat
import com.app.timelessmedicine.R
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.ui.selectlanguage.SelectLanguageActivity
import com.app.timelessmedicine.utils.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
       // window.setBackgroundDrawable(getDrawable(R.drawable.splash_main))
        /*if(SharedPreferenceUtil.getInstance(this).isLoggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {*/
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, SelectLanguageActivity::class.java))
            finishAffinity()
        }, 2000)

       // }
    }
}
