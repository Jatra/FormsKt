package uk.co.jatra.dateedit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.ScrollView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        DateInputMask(editText)
        editText.addTextChangedListener(MMYYWatcher())
        phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        scroller.keepFocussedChildAtTop(50)
    }

    fun ScrollView.keepFocussedChildAtTop(offset: Int) {
        val scrollerChild: View? = getChildAt(0)
        scrollerChild?.let {
            addFocusScroller(scrollerChild, offset)
        }
    }

    private fun ScrollView.addFocusScroller(scrollerChild: View, offset: Int) {
        if (scrollerChild is ViewGroup) {
            for (i in 0 until scrollerChild.childCount) {
                this.addFocusScroller(scrollerChild.getChildAt(i), offset)
            }
        }
        else {
            scrollerChild.addOnFocusChangeListener(OnFocusChangeListener({ view, hasFocus ->
                if (hasFocus) {
                    val array = intArrayOf(0, 0)
                    view.getLocationInWindow(array)
                    smoothScrollTo(0, array[1] - offset)
                }
            }))
        }
    }

    fun View.addOnFocusChangeListener(listener: OnFocusChangeListener) {
        onFocusChangeListener = if (onFocusChangeListener != null) {
            OnFocusChangeListener(
                    { v, hasFocus ->
                        onFocusChangeListener.onFocusChange(v, hasFocus)
                        listener.onFocusChange(v, hasFocus)
                    }
            )
        } else {
            listener
        }
    }
}


