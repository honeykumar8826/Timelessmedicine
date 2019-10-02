package com.app.timelessmedicine.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var first_name:String,
    var last_name:String,
    var email_id:String,
    var is_country:String,
    var is_language:String,
    var profile_image:String,
    var unique_id:String,
    var refercode:String,
    var access_token:String
)
