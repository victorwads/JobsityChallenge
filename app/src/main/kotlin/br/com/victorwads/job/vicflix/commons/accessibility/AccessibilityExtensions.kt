package br.com.victorwads.job.vicflix.commons.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.StringRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

class AccessibilityExtensions
private constructor(@StringRes val description: Int) : AccessibilityDelegateCompat() {

    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(host, info)

        info.addAction(
            AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                AccessibilityNodeInfo.ACTION_CLICK,
                host.context.getString(description)
            )
        )
    }

    companion object {
        fun View.configAccessibility(@StringRes actionDescription: Int) =
            ViewCompat.setAccessibilityDelegate(this, AccessibilityExtensions(actionDescription))
    }
}
