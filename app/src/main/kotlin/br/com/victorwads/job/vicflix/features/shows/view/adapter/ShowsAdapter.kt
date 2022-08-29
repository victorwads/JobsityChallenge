package br.com.victorwads.job.vicflix.features.shows.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding

class ShowsAdapter(
    private val inflater: LayoutInflater,
    private val onSelectShow: (Show) -> Unit,
) : RecyclerView.Adapter<ShowViewHolder>() {

    private var shows: MutableList<Show>? = null

    override fun getItemCount() = shows?.size ?: LOADING_FILL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShowViewHolder(ListingShowItemBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) = shows.let { items ->
        if (items == null) {
            holder.clear()
        } else {
            holder.bindData(items[position], onSelectShow)
        }
        return@let
    }

    fun clear() = shows?.run {
        itemCount.let {
            shows = null
            notifyItemRangeRemoved(LOADING_FILL, it - LOADING_FILL)
            notifyItemRangeChanged(0, LOADING_FILL)
        }
    }

    fun setItems(items: List<Show>) {
        clear()
        addItems(items)
    }

    fun addItems(items: List<Show>) {
        shows.let { oldList ->
            if (oldList == null) {
                shows = items.toMutableList()
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
