package com.app.timelessmedicine.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.app.timelessmedicine.bean.HomeBean
import com.app.timelessmedicine.ui.base.BaseViewModel

class HomeItemViewModel:BaseViewModel(){

    private val iv_referral = MutableLiveData<Int>()
    private val tv_referral = MutableLiveData<String>()
    private val position = MutableLiveData<Int>()
    private var mListener:ItemClickListener? = null


    fun bind(bean:HomeBean, position:Int){
        iv_referral.value = bean.resourceId
        tv_referral.value = bean.title
        this.position.value = position
    }

    fun getIv_referral():MutableLiveData<Int>{
        return iv_referral
    }

    fun getTv_referral():MutableLiveData<String>{
        return tv_referral
    }

    fun setMlistener(listener: ItemClickListener){
        mListener = listener
    }

    fun onItemClick(){
        mListener?.onItemClick(position.value!!)
    }

    interface ItemClickListener{
        fun onItemClick(position:Int)
    }

}