package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.databinding.ShowdetailsSeasonItemBinding

class SeasonHolder(
    private val layout: ShowdetailsSeasonItemBinding
) : ShowSeasonEpisodesAdapter.Holder(layout) {
    fun bind(season: Season) {
        layout.labelName.text = if (season.name.isNullOrEmpty()) {
            getString(R.string.showdetails_season, season.number)
        } else {
            getString(R.string.showdetails_season_name, season.number, season.name)
        }
    }
}