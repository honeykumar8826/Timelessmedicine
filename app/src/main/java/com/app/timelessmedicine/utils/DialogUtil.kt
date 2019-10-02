package com.app.timelessmedicine.utils

import android.app.*
import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.timelessmedicine.R
import java.util.*



object DialogUtil {

  /*  var mDialog:Dialog? = null

    fun showShortToast(msg: String, context: Context?) {

//    val toast = Toast.makeText(context,
//    msg, Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
//    toast.show()
        try {

            val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(
                R.layout.dialog_toast_message,
                (context as Activity).findViewById<ViewGroup>(R.id.customToastView)
            )

            layout.findViewById<TextView>(R.id.txtAlertMsgLine).text = msg
            val toast = Toast(context.getApplicationContext())
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.FILL, 0, 100)
            toast.view = layout
            toast.show()

        } catch (e: Exception) {
            e.stackTrace
        }

    }


    fun showErrorToast(msg: String, context: Context?) {

//    val toast = Toast.makeText(context,
//    msg, Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
//    toast.show()
        try {

            val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(
                R.layout.dialog_toast_error_message,
                (context as Activity).findViewById<ViewGroup>(R.id.customToastView)
            )

            layout.findViewById<TextView>(R.id.txtAlertMsgLine).text = msg
            val toast = Toast(context.getApplicationContext())
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.FILL, 0, 100)
            toast.view = layout
            toast.show()

        } catch (e: Exception) {
            e.stackTrace
        }

    }

    fun showShortToastWithImage(context:Context, msg:String){
        try {

            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(
                R.layout.dialog_toast_message,
                (context as Activity).findViewById<ViewGroup>(R.id.customToastView)
            )

            layout.findViewById<TextView>(R.id.txtAlertMsgLine).text = msg
            layout.findViewById<ImageView>(R.id.ivSuccess).setImageResource(R.drawable.verify_successfully)
            val toast = Toast(context.getApplicationContext())
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.FILL, 0, 100)
            toast.view = layout
            toast.show()

        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun showMessage(ctx:Context, message:String){

        mDialog = Dialog(ctx)
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)


        mDialog?.setContentView(R.layout.dialog_message)
        mDialog?.setCancelable(true)
        mDialog?.setCanceledOnTouchOutside(true)
        mDialog?.show()

        val tv_message = mDialog?.tv_message
        val btn_ok = mDialog?.btn_ok

        tv_message?.text = message

        btn_ok?.setOnClickListener {
            mDialog?.dismiss()
        }
    }

    fun showDatePicker(ctx: Context, textView:TextView){
        val cal = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            ctx, AlertDialog.THEME_HOLO_LIGHT,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                textView.text = "$dayOfMonth-${EnumUtils.MONTHS.values()[month]}-$year"

                AppConstant.day = dayOfMonth
                AppConstant.month = month
                AppConstant.year = year

            },
            cal[Calendar.YEAR],
            cal[Calendar.MONTH],
            cal[Calendar.DAY_OF_MONTH]


        )
        // datePickerDialog.datePicker.minDate = (System.currentTimeMillis() - (5*12*30*24*60*60*1000))
        // cal.add(Calendar.YEAR, -10)
        datePickerDialog.datePicker.minDate = cal.timeInMillis
        datePickerDialog.show()
    }

    fun showTimePicker(ctx:Context, textView: TextView){
        val cal = Calendar.getInstance()


        val timePickerDialog = TimePickerDialog(ctx, TimePickerDialog.OnTimeSetListener { timePicker, i, j ->

            val hour = String.format("%02d", i)
            val minute = String.format("%02d", j)

            var ampm = ""
            if (i<12) {
                ampm =  "AM"
            } else {
                ampm = "PM"
            }

            textView.text = "${hour}:${minute} $ampm"

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, i)
            calendar.set(Calendar.MINUTE, j)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val millis = calendar.timeInMillis

            AppConstant.schedule_time = millis


        }, cal[Calendar.HOUR_OF_DAY], cal[Calendar.MINUTE], true)

        timePickerDialog.show()
    }

*/


}