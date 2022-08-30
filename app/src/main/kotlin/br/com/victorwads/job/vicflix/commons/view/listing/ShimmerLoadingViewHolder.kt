package br.com.victorwads.job.vicflix.commons.view.listing

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.view.accessibility.AccessibilityExtensions.Companion.configAccessibility
import com.facebook.shimmer.ShimmerFrameLayout

abstract class ShimmerLoadingViewHolder<Model>(
    private val root: View,
    private val shimmer: ShimmerFrameLayout,
    @StringRes private val selectActionDescription: Int,
) : RecyclerView.ViewHolder(root) {

    private val context = root.context

    protected abstract fun clear(): Any?

    protected abstract fun loaded(data: Model, onSelectItem: (Model) -> Unit): Any?

    fun bindLoading() {
        shimmer.showShimmer(true)
        with(root) {
            contentDescription = context.getString(R.string.listing_loading)
            setOnClickListener(null)
        }
        clear()
    }

    fun bindData(data: Model, onSelectItem: (Model) -> Unit) {
        shimmer.hideShimmer()
        with(root) {
            contentDescription = null
            configAccessibility(selectActionDescription)
            setOnClickListener { onSelectItem(data) }
        }
        loaded(data, onSelectItem)
    }
}
