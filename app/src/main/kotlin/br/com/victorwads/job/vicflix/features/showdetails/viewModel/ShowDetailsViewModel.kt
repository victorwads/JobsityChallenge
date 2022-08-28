package br.com.victorwads.job.vicflix.features.showdetails.viewModel

import androidx.lifecycle.*
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
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

    fun loadEpisodes(season: Season, owner: LifecycleOwner) =
        MutableLiveData<ShowSeasonStates>().apply {
            observe(owner) {
                if (it is ShowSeasonStates.Load) {
                    viewModelScope.launch(Main) {
                        val episodes = repository.getEpisodes(season)
                        value = ShowSeasonStates.EpisodesLoaded(episodes)
                    }
                }
            }
        }
}