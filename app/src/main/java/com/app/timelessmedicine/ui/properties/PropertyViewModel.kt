package com.app.timelessmedicine.ui.properties

import androidx.lifecycle.LiveData
import com.app.timelessmedicine.bean.response.oilblends.OilBlendBean
import com.app.timelessmedicine.bean.response.property.PropertyBean
import com.app.timelessmedicine.ui.base.BaseViewModel
import com.app.timelessmedicine.ui.oilsandblends.OilBlendsRepo

class PropertyViewModel  : BaseViewModel() {

    var propertyRepo: PropertyRepo

    init {
        propertyRepo = PropertyRepo.getInstance()

    }



    fun getPropertyData() {
        propertyRepo.hitProperty()
       // return propertyRepo.getData()
    }
}