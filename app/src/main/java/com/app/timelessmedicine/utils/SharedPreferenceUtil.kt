package com.app.timelessmedicine.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.app.timelessmedicine.constant.AppConstant
import javax.inject.Inject


class SharedPreferenceUtil
@Inject constructor(val context: Context) {
    val TAG = SharedPreferenceUtil::class.java.simpleName

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstant.PREFRENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceUtil? = null

        fun getInstance(ctx: Context): SharedPreferenceUtil {
            if (instance == null) {
                instance = SharedPreferenceUtil(ctx)
            }
            return instance!!
        }
    }

    var isLoggedIn: Boolean
        get() = sharedPreferences["is_logged_in", false]!!
        set(value) = sharedPreferences.set("is_logged_in", value)

    var isIntroSlidesDone: Boolean
        get() = sharedPreferences["intro_slides", false]!!
        set(value) = sharedPreferences.set("intro_slides", value)

    var isLocationSelected: Boolean
        get() = sharedPreferences["is_location_selected", false]!!
        set(value) = sharedPreferences.set("is_location_selected", value)


    var accessToken: String
        get() = sharedPreferences["accessToken", ""]!!
        set(value) = sharedPreferences.set("accessToken", value)

    var deviceToken: String
        get() = sharedPreferences["deviceToken", ""]!!
        set(value) = sharedPreferences.set("deviceToken", value)

    var is_first_fcm_token: Boolean
        get() = sharedPreferences["IS_FIRST_FCM_TOKEN", false]!!
        set(value) = sharedPreferences.set("IS_FIRST_FCM_TOKEN", value)


    var user_id: String
        get() = sharedPreferences["user_id", ""]!!
        set(value) = sharedPreferences.set("user_id", value)



    var email: String
        get() = sharedPreferences["email", ""]!!
        set(value) = sharedPreferences.set("email", value)

    var mobileNumber: String
        get() = sharedPreferences["mobileNumber", ""].toString()
        set(value) = sharedPreferences.set("mobileNumber", value)

    var is_mobile_verified: String
        get() = sharedPreferences["is_mobile_verified", ""]!!
        set(value) = sharedPreferences.set("is_mobile_verified", value)

    var is_profile_created: String
        get() = sharedPreferences["is_profile_created", ""]!!
        set(value) = sharedPreferences.set("is_profile_created", value)

    var name: String
        get() = sharedPreferences["name", ""]!!
        set(value) = sharedPreferences.set("name", value)

    var profileImage: String
        get() = sharedPreferences["profileImage", ""]!!
        set(value) = sharedPreferences.set("profileImage", value)

    var latitude: String
        get() = sharedPreferences["latitude", ""]!!
        set(value) = sharedPreferences.set("latitude", value)

    var longitude: String
        get() = sharedPreferences["longitude", ""]!!
        set(value) = sharedPreferences.set("longitude", value)

    var is_driver: Int
        get() = sharedPreferences["is_driver", 0]!!
        set(value) = sharedPreferences.set("is_driver", value)

    var is_verified: Int
        get() = sharedPreferences["is_verified", 0]!!
        set(value) = sharedPreferences.set("is_verified", value)

    var address: String
        get() = sharedPreferences["address", ""]!!
        set(value) = sharedPreferences.set("address", value)

    var gender: String
        get() = sharedPreferences["gender", ""]!!
        set(value) = sharedPreferences.set("gender", value)
    var dob: String
        get() = sharedPreferences["dob", ""]!!
        set(value) = sharedPreferences.set("dob", value)
    var bloodGroup: String
        get() = sharedPreferences["bloodGroup", ""]!!
        set(value) = sharedPreferences.set("bloodGroup", value)
    var maritalStatus: String
        get() = sharedPreferences["maritalStatus", ""]!!
        set(value) = sharedPreferences.set("maritalStatus", value)
    var height: String
        get() = sharedPreferences["height", ""]!!
        set(value) = sharedPreferences.set("height", value)

    var weight: String
        get() = sharedPreferences["weight", ""]!!
        set(value) = sharedPreferences.set("weight", value)

    var emergencyContact: String
        get() = sharedPreferences["emergencyContact", ""]!!
        set(value) = sharedPreferences.set("emergencyContact", value)
    var city: String
        get() = sharedPreferences["city", ""]!!
        set(value) = sharedPreferences.set("city", value)

    var countryCode: String
        get() = sharedPreferences["countryCode", ""]!!
        set(value) = sharedPreferences.set("countryCode", value)


    var categoryHome: String
        get() = sharedPreferences["categoryHome", ""]!!
        set(value) = sharedPreferences.set("categoryHome", value)

    var showHideNo: String
        get() = sharedPreferences["showHide", ""]!!
        set(value) = sharedPreferences.set("showHide", value)

    var country: String
        get() = sharedPreferences["country", ""]!!
        set(value) = sharedPreferences.set("country", value)

    var language: String
        get() = sharedPreferences["language", ""]!!
        set(value) = sharedPreferences.set("language", value)




    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> Log.e(TAG, "Setting shared pref failed for key: $key and value: $value ")
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun deletePreferences() {
        editor.clear()
        editor.apply()
    }


}