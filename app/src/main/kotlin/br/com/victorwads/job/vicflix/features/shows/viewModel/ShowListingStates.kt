package br.com.victorwads.job.vicflix.features.shows.viewModel

import br.com.victorwads.job.vicflix.features.shows.model.Show

sealed class ShowListingStates {

    data class SearchedShows(val shows: List<Show>) : ShowListingStates()
    data class AddShows(val shows: List<Show>) : ShowListingStates()
    data class Favorites(val shows: List<Show>) : ShowListingStates()
    data class Error(val error: String = "generic error") : ShowListingStates()
    object Loading : ShowListingStates()
}
