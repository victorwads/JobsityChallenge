package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.accessibility.AccessibilityExtensions.Companion.configAccessibility
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.view.Navigation
import br.com.victorwads.job.vicflix.databinding.ShowdetailsEpisodeItemBinding

class EpisodeHolder(
    private val layout: ShowdetailsEpisodeItemBinding
) : ShowSeasonEpisodesAdapter.Holder(layout) {
    fun bind(season: Season, episode: Episode, number: Int, navigation: Navigation) = layout.apply {
        if (episode.name.isNullOrEmpty()) {
            bind(episode.number)
        } else {
            root.setOnClickListener {
                navigation.openEpisodeDetails(episode)
            }
            labelName.text = getString(
                R.string.showdetails_episode_name,
                season.number, number, episode.name
            )
            labelName.contentDescription = getString(
                R.string.showdetails_episode_name_description,
                season.number, number, episode.name
            )
            labelName.configAccessibility(R.string.showdetails_episode_action_description)
        }
        shimmer.hideShimmer()
    }

    fun bind(episode: Int) = layout.labelName.apply {
        text = getString(R.string.showdetails_episode, episode)
    }
}
