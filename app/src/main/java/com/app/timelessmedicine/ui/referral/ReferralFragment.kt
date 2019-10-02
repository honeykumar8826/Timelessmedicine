package com.app.timelessmedicine.ui.referral


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.FragmentReferralBinding
import com.app.timelessmedicine.ui.base.BaseFragment

class ReferralFragment : BaseFragment<FragmentReferralBinding, ReferralFragmentViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_referral


    var ctx: Context? = null
    private lateinit var referralViewModel: ReferralFragmentViewModel
    private lateinit var binding:FragmentReferralBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        referralViewModel = ViewModelProviders.of(this).get(ReferralFragmentViewModel::class.java)
    }

}
