package br.com.victorwads.job.vicflix.features.shows.viewModel

import br.com.victorwads.job.vicflix.commons.repositories.model.Show

sealed class ShowListingStates {

    data class CleanAddShows(val shows: List<Show>) : ShowListingStates()
    data class AddShows(val shows: List<Show>) : ShowListingStates()
    data class Error(val error: String = "generic error") : ShowListingStates()
    object Loading : ShowListingStates()
    object ShowsEnded : ShowListingStates()
}
