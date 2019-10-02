package com.app.timelessmedicine.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.ActivityHomeBinding
import com.app.timelessmedicine.ui.base.BaseActivity
import com.app.timelessmedicine.ui.homepage.HomeFragment
import com.app.timelessmedicine.ui.profile.ProfileFragments
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>(),View.OnClickListener {

    var fragment:Fragment? = null

    override fun onClick(p0: View?) {
        when(R.id.btnHome){
            R.id.btnHome->{
                changeFragment(HomeFragment())
            }
        }
    }

    private lateinit var homeViewModel:HomeActivityViewModel
    private lateinit var binding:ActivityHomeBinding

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_home)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = window.decorView
            var flags = view.setSystemUiVisibility(0)
           // view.systemUiVisibility = flags
        }
        homeViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel::class.java)
        binding = viewDataBinding!!
        viewModel = homeViewModel
        init()
        initControl()
    }

    fun init(){
        cardView.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()
    }

    fun initControl(){
        btnHome.setOnClickListener(this)
    }

    fun changeFragment(fragment: Fragment){
        this.fragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun hideBottomView(){
        cardView.visibility = View.GONE
    }

    fun showBottomView(){
        cardView.visibility = View.VISIBLE
    }

    fun isInSameFragment(fragment: String): Boolean {
        for (item in supportFragmentManager.fragments) {
            if (item.isVisible && fragment == item.javaClass.simpleName) {
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(fragment != null){
            if(fragment is ProfileFragments){
                fragment!!.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
