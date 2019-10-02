package com.app.timelessmedicine.ui.oilandblenddetail

import androidx.lifecycle.LiveData
import com.app.timelessmedicine.bean.response.blends.SingleBlendBean
import com.app.timelessmedicine.bean.response.oils.SingleOilBean
import com.app.timelessmedicine.ui.base.BaseViewModel

class OilBlendDetailViewModel : BaseViewModel() {
    private lateinit var oilBlendDetailRepo: OilBlendDetailRepo
    fun inIt() {

        oilBlendDetailRepo = OilBlendDetailRepo.getInstance()
    }

    fun getSingleOilData(oilName: String): LiveData<SingleOilBean> {
        return oilBlendDetailRepo.hitOil(oilName)
    }
    fun getSingleBlendData(blendName: String): LiveData<SingleBlendBean> {
        return oilBlendDetailRepo.hitBlend(blendName)
    }
}