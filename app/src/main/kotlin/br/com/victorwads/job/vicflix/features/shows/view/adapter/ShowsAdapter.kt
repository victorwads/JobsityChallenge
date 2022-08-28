package br.com.victorwads.job.vicflix.features.shows.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.accessibility.AccessibilityExtensions.Companion.configAccessibility
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding
import com.squareup.picasso.Picasso

class ShowsAdapter(
    private val inflater: LayoutInflater,
    private val onSelectShow: (Show) -> Unit
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    var shows: ArrayList<Show> = arrayListOf()

    override fun getItemCount() = shows.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShowViewHolder(ListingShowItemBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bindData(shows[position], onSelectShow)
    }

    fun addItems(items: List<Show>, clean: Boolean) {
        if (clean) {
            shows.size.let {
                shows.clear()
                notifyItemRangeRemoved(0, it)
            }
        }
        shows.size.let {
            shows.addAll(items)
            notifyItemRangeInserted(it, items.size)
        }
    }

    class ShowViewHolder(
        private val layout: ListingShowItemBinding
    ) : RecyclerView.ViewHolder(layout.root) {

        fun bindData(show: Show, onSelectShow: (Show) -> Unit) = with(layout) {
            labelName.text = show.name
            show.image?.medium?.let { Picasso.get().load(it).fit().centerCrop().into(poster) }

            root.apply {
                configAccessibility(R.string.listing_select_action_description)
                setOnClickListener { onSelectShow(show) }
            }
        }
    }
}
