package com.app.timelessmedicine.bean.response.blends

data class Response(
    val Description: String,
    val Main_constituents: String,
    val Safety_instructions: String,
    val _id: String,
    val all_data: List<AllData>,
    val blends_name: String,
    val forAndroid_blends: List<ForAndroidBlend>,
    val objectdata: Objectdata,
    val word_characteristic: String
)