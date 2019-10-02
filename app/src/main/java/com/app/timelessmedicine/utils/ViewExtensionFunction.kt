import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.timelessmedicine.constant.AppConstant
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun TextView.underLine() {
    paint.flags = paint.flags or Paint.UNDERLINE_TEXT_FLAG
    paint.isAntiAlias = true
}


fun showShortToast(msg: String, context: Context?) {
//    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    val toast = Toast.makeText(
        context,
        msg, Toast.LENGTH_SHORT
    )
    //  toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.show()
}
fun showLongToast(msg: String, context: Context?) {
//    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    val toast = Toast.makeText(
        context,
        msg, Toast.LENGTH_LONG
    )
    //  toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.show()
}

fun dobFormat(selectedDate: String): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH) // 2018-10-03
    val targetFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = originalFormat.parse(selectedDate)

    return targetFormat.format(date)

}


fun openBrowser(context: Context, socialURL: String) {
    var url = socialURL
    if (!url.startsWith("https://") && !url.startsWith("http://")) {
        url = "http://$url"
    }
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}


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

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}



val String.isValidEmail: Boolean
    get() = if (this.length < 3 || this.length > 265)
        false
    else {
        this.matches(AppConstant.EMAIL_PATTERN.toRegex())
    }


fun containNumbersOnly(source: String): Boolean {

    val pattern = Pattern.compile("\\d+.\\d+") //correct pattern for both float and integer.

    val result = pattern.matcher(source).matches()
    if (!result)
        println("\"$source\" is a String")
    else {
        println("\"$source\" is a number")
    }
    return result
}


fun isValidEmail(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()

    }
}

fun isValidPhoneNumber(target: CharSequence): Boolean {
    //phone.length() < 6 || phone.length() > 13
    return if (target.length < 6 || target.length > 13) {
        false
    } else {
        android.util.Patterns.PHONE.matcher(target).matches()
    }
}


/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TagName() = this::class.simpleName







