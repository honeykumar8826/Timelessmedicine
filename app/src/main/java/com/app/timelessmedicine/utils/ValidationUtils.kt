package com.app.timelessmedicine.utils

import android.text.InputFilter
import android.widget.EditText
import java.util.regex.Pattern

object ValidationUtils {
    @JvmStatic
    fun emptyIfNull(input: String?): String {
        if (input == null) return ""
        return input
    }

    @JvmStatic
    fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @JvmStatic
    fun isValidPassword(password: String): Boolean {
        if (password.length in 8..16)  {
            return true
        }
        return false
    }

    @JvmStatic
    fun isValidMobile(phone: String): Boolean {
        // For Other Countries :  phone.length() < 6 || phone.length() > 13
        // For India : phone.length >= 10
        return phone.length in 6..14
        return android.util.Patterns.PHONE.matcher(phone).matches()

    }


    @JvmStatic
    fun isValidInputPassword(password: String, confirmPassword: String): Boolean {
        if (password.equals(confirmPassword)) {
            return true
        }
        return false
    }


    @JvmStatic
    fun EditText.disableSpace() {

        val spaceFilter = InputFilter { source, start, end, _, _, _ ->

            var r: CharSequence? = null
            for (i in start until end) {
                if (!Character.isLetterOrDigit(source[i])) {
                    r = ""
                }
            }
            r
        }

        this.filters = arrayOf(spaceFilter)


    }


}
