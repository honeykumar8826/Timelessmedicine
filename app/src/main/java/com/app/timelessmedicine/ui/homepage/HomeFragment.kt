package com.app.timelessmedicine.ui.homepage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.timelessmedicine.MyApplication
import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.FragmentHomeBinding
import com.app.timelessmedicine.ui.base.BaseFragment
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.ui.home.HomeActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeActivityViewModel>() {


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = viewDataBinding!!
        homeViewModel.injectDagger((activity!!.application as MyApplication).getAppComponent())
        binding.viewModel = homeViewModel
        homeViewModel.setData(activity!!)
        init()
    }

    fun init() {
        recyclerView.layoutManager = GridLayoutManager(activity!!, 2)
        recyclerView.adapter = AdapterHomePage()
    }


    override fun onResume() {
        super.onResume()
        if (activity!! is HomeActivity) {
            (activity!! as HomeActivity).hideBottomView()
        }
    }

}
