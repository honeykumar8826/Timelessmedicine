package com.app.timelessmedicine.retrofit

import com.app.timelessmedicine.bean.response.ResponseProfile
import com.app.timelessmedicine.bean.response.blends.SingleBlendBean
import com.app.timelessmedicine.bean.response.oilblends.OilBlendBean
import com.app.timelessmedicine.bean.response.oils.SingleOilBean
import com.app.timelessmedicine.bean.response.property.PropertyBean
import com.app.timelessmedicine.bean.response.propertydetail.SinglePropertyBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    @Multipart
    @POST(ApiConstant.ADDACCOUNT)
    fun addAccount(
        @Part("first_name") first_name: RequestBody?,
        @Part("last_name") last_name: RequestBody?,
        @Part("email_id") email_id: RequestBody?,
        @Part("device_type") device_type: RequestBody?,
        @Part("is_country") is_country: RequestBody?,
        @Part("is_language") is_language: RequestBody?,
        @Part profile_image: MultipartBody.Part? = null
    ): Observable<ResponseProfile>

    @Multipart
    @POST(ApiConstant.EDITPROFILE)
    fun editProfile(
        @Header("access_token") access_token: String?,
        @Part("first_name") first_name: RequestBody?,
        @Part("last_name") last_name: RequestBody?,
        @Part profile_image: MultipartBody.Part? = null
    ): Observable<ResponseProfile>

    @POST(ApiConstant.GET_OIL_BLEND)
    fun getAllOilAndBlend(): Observable<OilBlendBean>

    @FormUrlEncoded
    @POST(ApiConstant.GET_OIL_DETAIL)
    fun oilDetailOnly(
        @Field("oil_name") oil_name: String
    ): Observable<SingleOilBean>

    @FormUrlEncoded
    @POST(ApiConstant.GET_BLEND_DETAIL)
    fun blendDetailOnly(
        @Field("blends_name") blend_name: String
    ): Observable<SingleBlendBean>

    @POST(ApiConstant.GET_PROPERTY)
    fun getWholeProperty(): Observable<PropertyBean>

    @FormUrlEncoded
    @POST(ApiConstant.GET_PROPERTY_DETAIL)

    fun getSingleProperty(
        @Field("_id") property_name: String,
        @Field("index") property_index: Int): Observable<SinglePropertyBean>

   /* @POST(ApiConstant.SIGN_UP)
    fun signUp(
        @Body request: RequestSignUp
    ): Observable<ResponseSignUp>

    @POST(ApiConstant.LOGIN)
    fun loginUser(
        @Body request: ResponseLogin.RequestLogin
    ): Observable<ResponseSignUp>

    @POST(ApiConstant.VERIFYOTP)
    fun verifyOtp(
        @Query("_id") id: String,
        @Body verification_code: RequestOtp
    ): Observable<ResponseBean>

    @FormUrlEncoded
    @POST(ApiConstant.SENDOTP)
    fun sendotp(
        @Field("_id") id: String,
        @Field("mobile_number") mobile_number: String,
        @Field("country_code") country_code: String
    ): Observable<ResponseBean>

    @FormUrlEncoded
    @POST(ApiConstant.RESETPASSWORD)
    fun resetPassword(
        @Field("_id") id: String,
        @Field("password") password: String
    ): Observable<ResponseBean>

    @FormUrlEncoded
    @POST(ApiConstant.VERIFYACCOUNT)
    fun verifyAccount(
        @Field("id") id: String
    ): Observable<ResponseBean>

    @FormUrlEncoded
    @POST(ApiConstant.FORGOTPASSWORD)
    fun sendLink(
        @Field("email") email: String
    ): Observable<ResponseSignUp>

    @Multipart
    @POST(ApiConstant.CREATEPROFILE)
    fun createProfile(
        @Header("access_token") accessToken: String,
        @Part("country") country: RequestBody?,
        @Part("prefered_gender") prefered_gender: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part profile_image: MultipartBody.Part?
    ): Observable<ResponseRegistrationComplete>

    @Multipart
    @POST(ApiConstant.UPLOADDOC)
    fun uploadDoc(
        @Header("access_token") accessToken: String,
        @Part national_id_front: MultipartBody.Part?,
        @Part national_id_back: MultipartBody.Part?,
        @Part driving_lisence_front: MultipartBody.Part?,
        @Part driving_lisence_back: MultipartBody.Part?
    ): Observable<ResponseRegistrationComplete>

    @POST(ApiConstant.CHECKSOCIAL)
    fun checkSocial(
        @Body request: RequestSocialCheck
    ): Observable<ResponseSignUp>


    @POST(ApiConstant.SOCIALSIGNUP)
    fun socialSignUp(
        @Body request:RequestSocial
    ): Observable<ResponseSignUp>


    @FormUrlEncoded
    @POST(ApiConstant.CHANGEPASSWORD)
    fun changePassword(
        @Header("access_token") accessToken: String,
        @Field("old_password") old_password: String,
        @Field("current_password") current_password: String
    ): Observable<ResponseSignUp>



    @POST(ApiConstant.GETUSERDATA)
    fun getHomeData(
        @Header("access_token") accessToken: String
    ): Observable<ResponseSignUp>

    @FormUrlEncoded
    @POST(ApiConstant.FINDCOMPANION)
    fun searchCompanion(
        @Header("access_token") accessToken: String,
        @Field("to_latitude") to_latitude: String,
        @Field("to_longitude") to_longitude: String,
        @Field("from_latitude") from_latitude: String,
        @Field("from_longitude") from_longitude: String,
        @Field("from_address") from_address: String,
        @Field("to_address") to_address: String,
        @Field("bagages_size") bagages_size: Int,
        @Field("passengers_size") passengers_size: Int,
        @Field("is_schedule") is_schedule: Int,
        @Field("request_time") request_time: Long
    ): Observable<ResponseComapnion>


    @FormUrlEncoded
    @POST(ApiConstant.COMPANIONREQUEST)
    fun requestCompanion(
        @Header("access_token") accessToken: String,
        @Field("companion_id[]") companion_id: ArrayList<String>,
        @Field("user_passengers") user_passengers: Int,
        @Field("user_bagages") user_bagages: Int,
        @Field("request_time") request_time: Long
    ): Observable<ResponseCompanionRequest>


    @FormUrlEncoded
    @POST(ApiConstant.ACCEPTCOMPANION)
    fun acceptCompanion(
        @Header("access_token") accessToken: String,
        @Field("companion_id") companion_id: String
    ): Observable<ResponseCompanionRequest>


    @FormUrlEncoded
    @POST(ApiConstant.REJECTCOMPANION)
    fun rejectCompanion(
        @Header("access_token") accessToken: String,
        @Field("companion_id") companion_id: String
    ): Observable<ResponseCompanionRequest>

    @GET("https://maps.googleapis.com/maps/api/directions/json")
    fun getRoute(@Query("origin") origin: String,
                @Query("destination") destination: String,
                @Query("sensor") sensor: Boolean,
                @Query("key") key: String)
            : Observable<DirectionResults>


*/



}

