package br.com.victorwads.job.vicflix.features.shows.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorwads.job.vicflix.commons.repositories.retrofit.RetrofitProvider
import br.com.victorwads.job.vicflix.commons.view.listing.ListingViewModel
import br.com.victorwads.job.vicflix.features.favorites.repository.FavoritesRepository
import br.com.victorwads.job.vicflix.features.shows.repository.ShowsRepository
import br.com.victorwads.job.vicflix.features.shows.repository.ShowsService
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ShowListingViewModel(
    context: Context,
    private val repository: ShowsRepository =
        ShowsRepository(RetrofitProvider.instance.create(ShowsService::class.java), context)
) : ViewModel(), ListingViewModel {

    private val favoritesRepository = FavoritesRepository(context)
    val state = MutableLiveData<ShowListingStates>()
    var favorite: Boolean = false
        set(value) {
            if (value) loadFavorites()
            else loadMore()
            field = value
        }
    override var hasMorePages = true
        get() = state.value is ShowListingStates.AddShows && field

    private fun loadFavorites() {
        state.value = ShowListingStates.Loading
        viewModelScope.launch(Main) {
            repository.clear()
            state.value = ShowListingStates.Favorites(
                favoritesRepository.getAll().sortedBy { it.name }
            )
        }
    }

    override fun loadMore() {
        if (state.value !is ShowListingStates.AddShows) {
            state.value = ShowListingStates.Loading
        }
        viewModelScope.launch(Main) {
            val shows = repository.getMoreShows()
            if (shows == null) {
                state.value = ShowListingStates.Error()
                return@launch
            }
            if (shows.isEmpty()) {
                hasMorePages = false
                return@launch
            }
            state.value = ShowListingStates.AddShows(shows)
        }
    }

    override fun search(query: String) {
        state.value = ShowListingStates.Loading
        viewModelScope.launch(Main) {
            repository.clear()
            if (query.isEmpty()) {
                loadMore()
                return@launch
            }
            val shows = repository.search(query)
            state.value = ShowListingStates.SearchedShows(shows ?: listOf())
        }
    }
}
