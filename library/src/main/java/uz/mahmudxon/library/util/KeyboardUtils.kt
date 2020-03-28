package uz.mahmudxon.library.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {
    fun showSoftKeyboard(activity: Activity, et: EditText) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        et.requestFocus()
        et.isCursorVisible = true
    }

    fun hideSoftKeyboard(activity: Activity)
    {
        activity.hideKeyboard()
    }
}