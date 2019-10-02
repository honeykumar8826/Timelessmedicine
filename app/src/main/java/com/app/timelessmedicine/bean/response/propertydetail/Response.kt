package com.app.timelessmedicine.bean.response.propertydetail

data class Response(
    val _id: String,
    val product_name: String,
    val product_name_with_index: List<ProductNameWithIndex>,
    val property_name: String
)