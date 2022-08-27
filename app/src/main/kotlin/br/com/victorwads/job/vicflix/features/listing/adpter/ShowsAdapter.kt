package br.com.victorwads.job.vicflix.features.listing.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding

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

    fun addItems(items: List<Show>) {
        val lastIndex = shows.size
        shows.addAll(items)
        notifyItemRangeInserted(lastIndex, items.size)
    }

    class ShowViewHolder(
        private val layout: ListingShowItemBinding
    ) : RecyclerView.ViewHolder(layout.root) {

        fun bindData(show: Show, onSelectShow: (Show) -> Unit) = with(layout) {
            labelName.text = show.name
            root.setOnClickListener {
                onSelectShow(show)
            }
        }
    }
}