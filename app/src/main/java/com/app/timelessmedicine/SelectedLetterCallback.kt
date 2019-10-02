package com.app.timelessmedicine

import android.widget.TextView


interface SelectedLetterCallback {
      fun clickItemPosition(key:Int,firstIndexOfLetter:Int)
      fun selectedPosition(textView: TextView?)
}