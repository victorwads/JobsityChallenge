package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.view.Navigation
import br.com.victorwads.job.vicflix.databinding.ShowdetailsEpisodeItemBinding
import br.com.victorwads.job.vicflix.databinding.ShowdetailsSeasonItemBinding
import br.com.victorwads.job.vicflix.features.showdetails.viewModel.ShowDetailsViewModel
import br.com.victorwads.job.vicflix.features.showdetails.viewModel.ShowSeasonStates

class ShowSeasonEpisodesAdapter(
    private val season: Season,
    private val inflater: LayoutInflater,
    private val navigation: Navigation,
    lifecycleOwner: LifecycleOwner,
    viewModel: ShowDetailsViewModel,
) : RecyclerView.Adapter<ShowSeasonEpisodesAdapter.Holder>() {

    private var episodes: List<Episode>? = null
    private val episodeLoader = viewModel.loadEpisodes(season, lifecycleOwner)

    init {
        episodeLoader.observe(lifecycleOwner) {
            if (it is ShowSeasonStates.EpisodesLoaded) updateEpisodes(it.episodes)
        }
    }

    /**
     * SuppressLint("NotifyDataSetChanged")
     * Sometimes the API returns the wrong number of episodes in SeasonDetails, and leaves the
     * UI with outdated information about episodes, so with notifyDataSetChanged() despite the
     * performance it is necessary to review the entire listing
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun updateEpisodes(episodes: List<Episode>) {
        this.episodes = episodes
        if (season.size != episodes.size) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(HEADER_OFFSET, episodes.size)
        }
    }

    private fun loadEpisodes() {
        episodeLoader.value = ShowSeasonStates.Load
    }

    override fun getItemViewType(position: Int): Int = if (position == SEASON_POSITION) {
        SEASON_TYPE
    } else EPISODE_TYPE

    override fun getItemCount(): Int = (episodes?.size ?: season.size ?: 0) + HEADER_OFFSET

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == SEASON_TYPE) {
            loadEpisodes()
            SeasonHolder(ShowdetailsSeasonItemBinding.inflate(inflater, parent, false))
        } else {
            EpisodeHolder(ShowdetailsEpisodeItemBinding.inflate(inflater, parent, false))
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when (holder) {
            is SeasonHolder -> holder.bind(season)
            is EpisodeHolder -> episodes?.getOrNull(position - HEADER_OFFSET)
                ?.let { holder.bind(season, it, position, navigation) } ?: holder.bind(position)
        }
    }

    abstract class Holder(private val layout: ViewBinding) : RecyclerView.ViewHolder(layout.root) {
        fun getString(@StringRes resId: Int, vararg args: Any) =
            layout.root.context.getString(resId, *args)
    }

    companion object {
        const val HEADER_OFFSET = 1
        const val SEASON_POSITION = 0
        const val SEASON_TYPE = 0
        const val EPISODE_TYPE = 1
    }
}
