package br.com.victorwads.job.vicflix.features.shows.viewModel

import br.com.victorwads.job.vicflix.commons.repositories.model.Show

sealed class ShowListingStates {

    data class CleanAddShows(val moreShows: List<Show>) : ShowListingStates()
    data class AddShows(val moreShows: List<Show>) : ShowListingStates()
    data class Error(val error: String) : ShowListingStates()
    object Loading : ShowListingStates()
    object HideLoading : ShowListingStates()
    object ShowsEnded : ShowListingStates()
}