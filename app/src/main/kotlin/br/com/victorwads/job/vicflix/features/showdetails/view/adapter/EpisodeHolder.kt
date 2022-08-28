package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.databinding.ShowdetailsEpisodeItemBinding

class EpisodeHolder(
    private val layout: ShowdetailsEpisodeItemBinding
) : ShowSeasonEpisodesAdapter.Holder(layout) {
    fun bind(episode: Episode) = layout.labelName.apply {
        if (episode.name.isNullOrEmpty()) {
            bind(episode.number)
        } else {
            text = episode.name
        }
    }

    fun bind(episode: Int) = layout.labelName.apply {
        text = getString(R.string.showdetails_episode, episode)
    }
}