package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.databinding.ShowdetailsSessionItemBinding

class ShowSeasonEpisodesAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ShowSeasonEpisodesAdapter.EpisodeHolder>() {

    private val items: ArrayList<Any> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EpisodeHolder(
        ShowdetailsSessionItemBinding.inflate(inflater, parent, false)
    )

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder
    }

    override fun getItemCount(): Int = items.size + HEADER_OFFSET

    class EpisodeHolder(
        val layout: ShowdetailsSessionItemBinding
    ) : RecyclerView.ViewHolder(layout.root) {

    }

    companion object {
        const val HEADER_OFFSET = 1
    }
}