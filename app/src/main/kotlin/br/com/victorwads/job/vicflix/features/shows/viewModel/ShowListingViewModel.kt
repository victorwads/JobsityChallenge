package br.com.victorwads.job.vicflix.features.shows.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorwads.job.vicflix.commons.repositories.retrofit.RetrofitProvider
import br.com.victorwads.job.vicflix.features.shows.repository.ShowsRepository
import br.com.victorwads.job.vicflix.features.shows.repository.ShowsService
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ShowListingViewModel(
    private val repository: ShowsRepository =
        ShowsRepository(RetrofitProvider.instance.create(ShowsService::class.java))
) : ViewModel() {

    val state = MutableLiveData<ShowListingStates>()

    init {
        loadMore()
    }

    fun loadMore() {
        viewModelScope.launch(Main) {
            state.value = ShowListingStates.Loading
            val shows = repository.getMoreShows()
            if (shows == null) {
                state.value = ShowListingStates.Error()
                return@launch
            }
            if (shows.isEmpty()) {
                state.value = ShowListingStates.ShowsEnded
                return@launch
            }
            state.value = ShowListingStates.AddShows(shows)
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Main) {
            repository.clear()
            if (query.isEmpty()) {
                state.value = ShowListingStates.CleanAddShows(arrayListOf())
                loadMore()
                return@launch
            }
            state.value = ShowListingStates.Loading
            val shows = repository.search(query)
            state.value = ShowListingStates.CleanAddShows(
                (shows ?: arrayListOf())
                    .sortedBy { it.score }
                    .map { it.show }
            )
        }
    }
}
