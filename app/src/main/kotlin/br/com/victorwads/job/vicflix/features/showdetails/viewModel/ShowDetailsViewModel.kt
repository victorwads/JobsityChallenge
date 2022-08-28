package br.com.victorwads.job.vicflix.features.showdetails.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.retrofit.RetrofitProvider
import br.com.victorwads.job.vicflix.features.showdetails.repository.SeasonsRepository
import br.com.victorwads.job.vicflix.features.showdetails.repository.SeasonsService
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ShowDetailsViewModel(
    val show: Show,
    private val repository: SeasonsRepository = SeasonsRepository(
        RetrofitProvider.instance.create(
            SeasonsService::class.java
        )
    )
) : ViewModel() {

    val state = MutableLiveData<ShowDetailsStates>()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch(Main) {
            state.value = ShowDetailsStates.Loading
            val seasons = repository.getSeasons(show)
            state.value = ShowDetailsStates.SeasonsLoaded(seasons)
        }
    }
}