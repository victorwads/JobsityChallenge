package br.com.victorwads.job.vicflix.commons.view.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LoaderListingAdapter<Model>(
    private val onSelectItem: (Model) -> Unit,
    private val onCreateViewHolder: (parent: ViewGroup) -> ShimmerLoadingViewHolder<Model>
) : RecyclerView.Adapter<ShimmerLoadingViewHolder<Model>>() {

    private var items: MutableList<Model>? = null

    override fun getItemCount() = items?.size ?: LOADING_FILL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: ShimmerLoadingViewHolder<Model>, position: Int) = items.let {
        if (it == null) {
            holder.bindLoading()
        } else {
            holder.bindData(it[position], onSelectItem)
        }
        return@let
    }

    fun clear() = items?.run {
        itemCount.let {
            items = null
            notifyItemRangeRemoved(LOADING_FILL, it - LOADING_FILL)
            notifyItemRangeChanged(0, LOADING_FILL)
        }
    }

    fun setItems(items: List<Model>) {
        clear()
        addItems(items)
    }

    fun addItems(items: List<Model>) {
        this.items.let { oldList ->
            if (oldList == null) {
                this.items = items.toMutableList()
                if (items.size > LOADING_FILL) {
                    notifyItemRangeChanged(0, LOADING_FILL)
                    notifyItemRangeInserted(LOADING_FILL - 1, items.size - LOADING_FILL)
                } else {
                    notifyDataSetChanged()
                }
            } else {
                oldList.addAll(items)
                notifyItemRangeInserted(oldList.size, items.size)
            }
        }
    }

    companion object {
        const val LOADING_FILL = 5 * 3
    }
}
