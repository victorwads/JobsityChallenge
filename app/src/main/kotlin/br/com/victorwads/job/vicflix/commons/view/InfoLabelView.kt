package br.com.victorwads.job.vicflix.commons.view

import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.AppCompatTextView
import br.com.victorwads.job.vicflix.R


class InfoLabelView constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int?,
    var autofill: Boolean = false,
    var label: String = EMPTY_STRING
) : AppCompatTextView(context, attrs, defStyleAttr ?: 0) {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int?)
            : this(context, attrs, defStyleAttr, false) {
        init(context, attrs)
    }

    private var text: String = getText().toString()

    private fun init(context: Context, attrs: AttributeSet?) {
        focusable = View.FOCUSABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.InfoLabelView).apply {
                autofill = getBoolean(R.styleable.InfoLabelView_autofill, false)
                getString(R.styleable.InfoLabelView_label)?.let { label = it }
                super.setText(getString(R.styleable.InfoLabelView_android_text))
                recycle()
            }
        }
    }

    private fun getTextFinalValue(): String = text.ifEmpty {
        if (autofill) context.getString(R.string.no_info)
        else EMPTY_STRING
    }.replace("\\<[^>]*>".toRegex(), "").trim()

    override fun setText(text: CharSequence?, type: BufferType?) {
        this.text = text?.toString() ?: EMPTY_STRING
        super.setText(
            Html.fromHtml("<b>$label</b> ${getTextFinalValue()}", Html.FROM_HTML_MODE_COMPACT),
            BufferType.NORMAL
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        MarginLayoutParams::class.java.cast(layoutParams)?.apply {
            val screen = context.resources.getDimension(R.dimen.screen_padding).toInt()
            val label = context.resources.getDimension(R.dimen.label_padding).toInt()
            leftMargin = ifEmpty(leftMargin, screen)
            rightMargin = ifEmpty(rightMargin, screen)
            topMargin = ifEmpty(topMargin, label)
            bottomMargin = ifEmpty(bottomMargin, label)
        }
    }

    private fun ifEmpty(current: Int, new: Int): Int = if (current == 0) new else current

    companion object {
        private const val EMPTY_STRING = ""
    }
}
