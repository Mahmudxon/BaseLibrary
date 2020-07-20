package uz.mahmudxon.library.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.model.SlidrPosition
import uz.mahmudxon.library.R
import uz.mahmudxon.library.util.startFragment


abstract class BaseFragment(@LayoutRes val resId: Int, val canswipe: Boolean = false) : Fragment() {
    internal var senderData: Any? = null

    private var isUseBackPress = true

    var slidrInterface: SlidrInterface? = null
    lateinit var oldview: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        oldview = inflater.inflate(resId, container, false)
        if (canswipe)
            oldview.setOnTouchListener { _, _ -> true }
        return if (canswipe) FrameLayout(requireContext()).apply {
            setBackgroundColor(Color.TRANSPARENT)
            addView(oldview)
        }
        else oldview

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onCreatedView(senderData)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                isUseBackPress = true
                onBackPressed()
                return@setOnKeyListener isUseBackPress
            } else return@setOnKeyListener false
        }
    }

    open fun onBackPressed() {
        isUseBackPress = false
    }

    abstract fun onCreatedView(senderData: Any?)

    @SuppressLint("ResourceType")
    fun startFragment(fragment: BaseFragment, senderData: Any? = null, isAnimate: Boolean) {
        fragment.senderData = senderData
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (isAnimate)
            transaction?.setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit
            )

        transaction?.replace(R.id.content, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ?.addToBackStack(fragment.hashCode().toString())
            ?.commit()
    }

    @SuppressLint("ResourceType")
    fun addFragment(fragment: BaseFragment, senderData: Any? = null, isAnimate: Boolean = false) {
        fragment.senderData = senderData
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (isAnimate)
            transaction?.setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit
            )

        transaction?.add(R.id.content, fragment)
            ?.addToBackStack(fragment.hashCode().toString())
            ?.commit()
    }


    override fun onResume() {
        super.onResume()
        if (canswipe && slidrInterface == null) {
            slidrInterface =
                Slidr.replace(oldview, SlidrConfig.Builder().position(SlidrPosition.LEFT).build())
        }
    }

    fun replaceAllFragments(fragment: BaseFragment, isAnimate: Boolean = true) {
        activity?.supportFragmentManager?.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        (activity as AppCompatActivity).startFragment(fragment, null, isAnimate = isAnimate)
    }

    fun finish() {
        activity?.supportFragmentManager?.popBackStack()
    }

    fun lockSwipable() {
        slidrInterface?.lock()
    }

    fun unlockSwipable() {
        slidrInterface?.unlock()
    }

    fun openFragment(f: BaseFragment) {
        addFragment(f, null, true)
    }


}
