package br.com.victorwads.job.vicflix.features.shows.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.accessibility.AccessibilityExtensions.Companion.configAccessibility
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ShowsAdapter(
    private val inflater: LayoutInflater,
    private val onSelectShow: (Show) -> Unit,
    private var autoFill: Boolean = true
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    private var shows: ArrayList<Show>? = null

    override fun getItemCount() = shows?.size
        ?: LOADING_FILL.takeIf { autoFill }
        ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShowViewHolder(ListingShowItemBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        shows?.let {
            holder.bindData(it[position], onSelectShow)
        }
    }

    fun startLoading() {
        clear()
        autoFill = true
        notifyItemRangeInserted(0, itemCount)
    }

    fun clear() {
        itemCount.let {
            autoFill = false
            shows = null
            notifyItemRangeRemoved(0, it)
        }
    }

    fun addItems(items: List<Show>, clean: Boolean = false) {
        if (clean) {
            clear()
        }
        (shows ?: arrayListOf()).let { newList ->
            newList.addAll(items)
            if (autoFill && shows == null) {
                shows = newList
                notifyItemRangeChanged(0, LOADING_FILL)
                notifyItemRangeInserted(LOADING_FILL - 1, newList.size - LOADING_FILL)
            } else {
                shows = newList
                notifyItemRangeInserted(newList.size, items.size)
            }
        }
    }

    class ShowViewHolder(
        private val layout: ListingShowItemBinding
    ) : RecyclerView.ViewHolder(layout.root) {

        fun bindData(show: Show, onSelectShow: (Show) -> Unit) = with(layout) {
            root.showShimmer(true)
            labelName.text = show.name
            show.image?.medium?.let {
                Picasso.get().load(it).fit().centerCrop()
                    .into(poster, object : Callback {
                        override fun onSuccess() = root.hideShimmer()
                        override fun onError(e: Exception?) = root.hideShimmer()
                    })
            }

            root.apply {
                configAccessibility(R.string.listing_select_action_description)
                setOnClickListener {
                    onSelectShow(show)
                }
            }
        }
    }

    companion object {
        const val LOADING_FILL = 5 * 3
    }
}
