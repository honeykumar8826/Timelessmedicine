package com.app.timelessmedicine.constant

interface AppConstant {
    companion object{

        const val PREFRENCE_NAME: String = "app_pref"
        const val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)\$"
        const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z]).{8,255}\$"
        const val REQUEST_CAMERA_IMAGE_CODE = 102
        const val REQUEST_GALLERY_IMAGE_CODE = 103
    }
}