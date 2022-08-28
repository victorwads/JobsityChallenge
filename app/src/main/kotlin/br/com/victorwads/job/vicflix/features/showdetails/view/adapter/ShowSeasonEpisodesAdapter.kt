package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.databinding.ShowdetailsEpisodeItemBinding
import br.com.victorwads.job.vicflix.databinding.ShowdetailsSeasonItemBinding

class ShowSeasonEpisodesAdapter(
    private val season: Season,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ShowSeasonEpisodesAdapter.Holder>() {

    private var episodes: List<Episode>? = null

    fun addEpisodes(episodes: List<Episode>) {
        this.episodes = episodes
        notifyItemRangeChanged(HEADER_OFFSET, episodes.size)
    }

    override fun getItemViewType(position: Int): Int =
        if (position == SEASON_POSITION) SEASON_TYPE
        else EPISODE_TYPE

    override fun getItemCount(): Int = (episodes?.size ?: season.size) + HEADER_OFFSET

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == SEASON_TYPE) SeasonHolder(
            ShowdetailsSeasonItemBinding.inflate(inflater, parent, false)
        ) else EpisodeHolder(
            ShowdetailsEpisodeItemBinding.inflate(inflater, parent, false)
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when (holder) {
            is SeasonHolder -> holder.bind(season)
            is EpisodeHolder -> episodes?.getOrNull(position - HEADER_OFFSET)
                ?.let { holder.bind(it) } ?: holder.bind(position - HEADER_OFFSET)
        }
    }

    abstract class Holder(private val layout: ViewBinding) : RecyclerView.ViewHolder(layout.root) {
        fun getString(@StringRes resId: Int, vararg args: Any) =
            layout.root.context.getString(resId, *args)
    }

    class SeasonHolder(val layout: ShowdetailsSeasonItemBinding) : Holder(layout) {
        fun bind(season: Season) {
            layout.labelName.text = if (season.name.isNullOrEmpty()) {
                getString(R.string.showdetails_season, season.number)
            } else {
                getString(R.string.showdetails_season_name, season.number, season.name)
            }
        }
    }

    class EpisodeHolder(val layout: ShowdetailsEpisodeItemBinding) : Holder(layout) {
        fun bind(episode: Episode) = layout.labelName.apply {
            if (episode.name.isNullOrEmpty()) {
                bind(episode.number)
            } else {
                text = getString(R.string.showdetails_episode_name, episode.number, episode.name)
            }
        }

        fun bind(episode: Int) = layout.labelName.apply {
            text = getString(R.string.showdetails_episode, episode)
        }
    }

    companion object {
        const val HEADER_OFFSET = 1
        const val SEASON_POSITION = 0
        const val SEASON_TYPE = 0
        const val EPISODE_TYPE = 1
    }
}