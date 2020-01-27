package uz.mahmudxon.library.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import uz.mahmudxon.library.R
import uz.mahmudxon.library.ui.BaseFragment


@SuppressLint("ShowToast")
fun BaseFragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

//toast
fun AppCompatActivity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

//inflate adapter view
fun ViewGroup.inflate(@LayoutRes resId: Int) =
    LayoutInflater.from(context).inflate(resId, this, false)


fun AppCompatActivity.removeFragment(fm: Fragment) {
    supportFragmentManager.beginTransaction().remove(fm).commit()
}

fun AppCompatActivity.startFragment(
    fragment: BaseFragment,
    senderData: Any? = null,
    isAnimate: Boolean = false
) {
    val resId = ViewModelProviders.of(this)[BaseFragmentViewModel::class.java].resId
    fragment.senderData = senderData
    val transition = supportFragmentManager.beginTransaction()
    if (isAnimate)
        transition.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )

    transition.replace(R.id.content, fragment).commit()
}

fun AppCompatActivity.addFragment(
    fragment: BaseFragment,
    senderData: Any? = null,
    isAnimate: Boolean = false
) {
    val resId = ViewModelProviders.of(this)[BaseFragmentViewModel::class.java].resId
    fragment.senderData = senderData
    val transaction = supportFragmentManager.beginTransaction()

    if (isAnimate)
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
    transaction.add(R.id.content, fragment).addToBackStack(fragment.hashCode().toString())
        .commitAllowingStateLoss()
}

fun AppCompatActivity.clearFragmentManager() {
    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun AppCompatActivity.initialFragment(@IdRes resId: Int) {
    ViewModelProviders.of(this)[BaseFragmentViewModel::class.java].resId = resId
}

fun BaseFragment.initialFragment(@IdRes resId: Int) {
    ViewModelProviders.of(this)[BaseFragmentViewModel::class.java].resId = resId
}

class BaseFragmentViewModel : ViewModel() {
    internal var resId: Int = R.id.content
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun View?.blockClickable(blockTimeMilles: Long = 500) {
    this?.isClickable = false
    Handler().postDelayed({ this?.isClickable = true }, blockTimeMilles)
}

