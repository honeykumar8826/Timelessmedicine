package com.app.timelessmedicine.bean.response.oils

data class Response(
    val Blends_well_with: String,
    val Description: String,
    val Emotional_Index: String,
    val Found_in_blend: String,
    val Main_constituents: String,
    val Main_properties: String,
    val Safety_instructions: String,
    val _id: String,
    val all_data: List<AllData>,
    val latin_name: String,
    val local_name: String,
    val objectdata: Objectdata,
    val oil_name: String,
    // adding one new field
    val blends_name: String,
    val word_characteristic: String
)