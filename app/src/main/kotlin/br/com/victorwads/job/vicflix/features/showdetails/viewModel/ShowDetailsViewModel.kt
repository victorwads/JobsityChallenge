package br.com.victorwads.job.vicflix.features.showdetails.viewModel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.retrofit.RetrofitProvider
import br.com.victorwads.job.vicflix.features.favorites.repository.FavoritesRepository
import br.com.victorwads.job.vicflix.features.showdetails.repository.SeasonsRepository
import br.com.victorwads.job.vicflix.features.showdetails.repository.SeasonsService
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ShowDetailsViewModel(context: Context, val show: Show) : ViewModel() {

    private val seasonsRepository = SeasonsRepository(RetrofitProvider.instance.create(SeasonsService::class.java))
    private val favoritesRepository = FavoritesRepository(context)

    var favorite: Boolean
        get() = favoritesRepository.isFavorite(show)
        set(value) {
            if (value) {
                favoritesRepository.add(show)
            } else {
                favoritesRepository.remove(show)
            }
        }

    val state = MutableLiveData<ShowDetailsStates>()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch(Main) {
            state.value = ShowDetailsStates.Loading
            val seasons = seasonsRepository.getSeasons(show)
            if (seasons == null) {
                state.value = ShowDetailsStates.Error()
                return@launch
            }
            state.value = ShowDetailsStates.SeasonsLoaded(seasons)
        }
    }

    fun loadEpisodes(season: Season, owner: LifecycleOwner) =
        MutableLiveData<ShowSeasonStates>().apply {
            observe(owner) {
                if (it is ShowSeasonStates.Load) {
                    viewModelScope.launch(Main) {
                        val episodes = seasonsRepository.getEpisodes(season) ?: return@launch
                        value = ShowSeasonStates.EpisodesLoaded(episodes)
                    }
                }
            }
        }
}
