package br.com.victorwads.job.vicflix.features.showdetails.viewModel

import br.com.victorwads.job.vicflix.commons.repositories.model.Episode

sealed class ShowSeasonStates {

    data class EpisodesLoaded(val episodes: List<Episode>) : ShowSeasonStates()
    object Load : ShowSeasonStates()
}
