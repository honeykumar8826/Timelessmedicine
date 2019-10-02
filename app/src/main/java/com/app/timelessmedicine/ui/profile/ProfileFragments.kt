package com.app.timelessmedicine.ui.profile


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.timelessmedicine.R
import com.app.timelessmedicine.databinding.FragmentProfileFragmentsBinding
import com.app.timelessmedicine.ui.base.BaseFragment
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.ui.profileviewedit.ProfileViewEditFragment
import com.app.timelessmedicine.ui.referral.ReferralFragment
import kotlinx.android.synthetic.main.toolbar_back_title.*


class ProfileFragments : BaseFragment<FragmentProfileFragmentsBinding, ProfileViewModel>(),View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnReferral->{
                mFragment = ReferralFragment()
                changeFragment(mFragment!!)
                binding.btnReferral.setBackgroundResource(R.drawable.button_profile_bg)
                binding.btnMe.setBackgroundResource(R.drawable.button_profile_white_bg)
            }
            R.id.btnMe->{
                mFragment = ProfileViewEditFragment()
                changeFragment(mFragment!!)
                binding.btnMe.setBackgroundResource(R.drawable.button_profile_bg)
                binding.btnReferral.setBackgroundResource(R.drawable.button_profile_white_bg)
            }
        }
    }

    private lateinit var profileViewModel:ProfileViewModel
    private lateinit var binding:FragmentProfileFragmentsBinding
    var mFragment:Fragment? = null

    override val layoutId: Int
        get() = R.layout.fragment_profile_fragments


 /*   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragments, container, false)
    }
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding = viewDataBinding!!
        init()
        if(activity!! is HomeActivity){
            (activity as HomeActivity).showBottomView()
        }
    }

    fun init(){
        toolbar.setNavigationOnClickListener {
            if(activity is HomeActivity){
                activity?.onBackPressed()
            }
        }
        tv_title.text = getString(R.string.profile)
        mFragment = ProfileViewEditFragment()
        changeFragment(mFragment!!)

        binding.btnReferral.setOnClickListener(this)
        binding.btnMe.setOnClickListener(this)

    }

    fun changeFragment(fragment:Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(mFragment is ProfileViewEditFragment){
            mFragment?.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun isInSameFragment(fragment: String): Boolean {
        for (item in childFragmentManager.fragments) {
            if (item.isVisible && fragment == item.javaClass.simpleName) {
                return true
            }
        }
        return false
    }

}
