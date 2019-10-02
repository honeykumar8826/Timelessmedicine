package com.app.timelessmedicine.bean.response.oilblends

data class Oil(
    val Blends_well_with: String,
    val Description: String,
    val Emotional_Index: String,
    val Found_in_blend: String,
    val Main_constituents: String,
    val Main_properties: String,
    val SERIAL_NO: Int,
    val Safety_instructions: String,
    val _id: String,
    val all_data: List<AllDataX>,
    val latin_name: String,
    val local_name: String,
    val objectdata: ObjectdataX,
    val oil_name: String,
    val splitarray: List<Splitarray>,
    val word_characteristic: String
)