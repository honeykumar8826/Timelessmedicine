

interface ApiConstant {

    companion object {

        const val SERVER_URL = "http://15.206.8.58:3000/"
        //const val BASE_URL = SERVER_URL + "api/"
        const val BASE_URL = SERVER_URL
        const val TERM_CONDITIONS_URL = SERVER_URL + ""
        const val HELP_URL = SERVER_URL + ""
        const val IMAGE_BASE_URL = "http://15.206.8.58:3000"
        //const val IMAGES_RENT_IMAGES_URL = "http://3.17.57.110/GoodsAndRental/storage/UploadsRents/"


        /****************APIs Constant*****************/
        const val EDITPROFILE = "user/editProfile"
        const val ADDACCOUNT = "user/addaccount"
        const val GET_OIL_BLEND = "user/getalloildata"
        const val GET_OIL_DETAIL = "user/getsingleoildata"
        const val GET_BLEND_DETAIL = "user/getsingleblendsdata"
        const val GET_PROPERTY = "user/getpropertydata"
        const val GET_PROPERTY_DETAIL = "user/findsinglepropertydata"

    }
}