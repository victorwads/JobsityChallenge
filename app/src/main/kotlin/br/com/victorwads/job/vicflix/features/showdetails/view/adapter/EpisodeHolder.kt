package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.databinding.ShowdetailsEpisodeItemBinding

class EpisodeHolder(
    private val layout: ShowdetailsEpisodeItemBinding
) : ShowSeasonEpisodesAdapter.Holder(layout) {
    fun bind(season: Season, episode: Episode, number: Int) = layout.labelName.apply {
        if (episode.name.isNullOrEmpty()) {
            bind(episode.number)
        } else {
            text = getString(
                R.string.showdetails_episode_name,
                season.number, number, episode.name
            )
        }
    }

    fun bind(episode: Int) = layout.labelName.apply {
        text = getString(R.string.showdetails_episode, episode)
    }
}