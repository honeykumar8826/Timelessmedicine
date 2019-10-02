package com.app.timelessmedicine.ui.oilsandblends

import androidx.lifecycle.LiveData
import com.app.timelessmedicine.bean.response.oilblends.OilBlendBean
import com.app.timelessmedicine.ui.base.BaseViewModel

class OilBlendsViewModel : BaseViewModel() {

    private lateinit var oilBlendsRepo: OilBlendsRepo

    fun inIt() {

        oilBlendsRepo = OilBlendsRepo.getInstance()
    }

    fun getOilAndBlend(): LiveData<OilBlendBean> {
        return oilBlendsRepo.getOilAndBlendData()
    }
}