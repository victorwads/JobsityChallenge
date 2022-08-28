package br.com.victorwads.job.vicflix.commons.view

import android.content.Context
import android.text.Spannable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.databinding.CustomViewInfoLabelBinding

class InfoLabelView : LinearLayoutCompat {

    private val layout = CustomViewInfoLabelBinding.inflate(LayoutInflater.from(context), this)

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int?) : super(context, attrs, defStyleAttr ?: 0) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        focusable = View.FOCUSABLE
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.InfoLabelView).apply {
                autofill = getBoolean(R.styleable.InfoLabelView_autofill, false)
                text = getString(R.styleable.InfoLabelView_android_text)
                getString(R.styleable.InfoLabelView_label)?.let { label = it }
            }
        }
    }

    var autofill = false

    var label: CharSequence
        get() = layout.label.text
        set(value) {
            layout.label.text = value
        }

    var text: CharSequence?
        get() = layout.value.text
        set(value) {
            layout.value.text = if (value.isNullOrEmpty()) {
                if (autofill)
                    context.getString(R.string.no_info)
                else {
                    layout.value.visibility = View.GONE
                    value
                }
            } else {
                layout.value.visibility = View.VISIBLE
                value
            }
        }
}
