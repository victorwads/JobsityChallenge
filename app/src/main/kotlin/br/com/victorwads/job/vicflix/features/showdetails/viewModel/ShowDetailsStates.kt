package br.com.victorwads.job.vicflix.features.showdetails.viewModel

import br.com.victorwads.job.vicflix.commons.repositories.model.Season

sealed class ShowDetailsStates {

    data class SeasonsLoaded(val seasons: List<Season>) : ShowDetailsStates()
    data class Error(val error: String) : ShowDetailsStates()
    object Loading : ShowDetailsStates()
    object HideLoading : ShowDetailsStates()
}
