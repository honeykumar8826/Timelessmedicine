package com.app.timelessmedicine.bean.response.oilblends

data class Blend(
    val Description: String,
    val Main_constituents: String,
    val SERIAL_NO: Int,
    val Safety_instructions: String,
    val _id: String,
    val all_data: List<AllData>,
    val blends_name: String,
    val objectdata: Objectdata,
    val word_characteristic: String
)