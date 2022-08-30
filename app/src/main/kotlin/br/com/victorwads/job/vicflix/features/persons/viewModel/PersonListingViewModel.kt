package br.com.victorwads.job.vicflix.features.persons.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorwads.job.vicflix.commons.repositories.retrofit.RetrofitProvider
import br.com.victorwads.job.vicflix.commons.view.listing.ListingViewModel
import br.com.victorwads.job.vicflix.features.persons.repository.PersonsRepository
import br.com.victorwads.job.vicflix.features.persons.repository.PersonsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonListingViewModel(
    private val repository: PersonsRepository =
        PersonsRepository(RetrofitProvider.instance.create(PersonsService::class.java))
) : ViewModel(), ListingViewModel {

    val state = MutableLiveData<PersonListingStates>()

    override var hasMorePages = false

    override fun loadMore() = Unit

    override fun search(query: String) {
        if (query.isEmpty()) {
            state.value = PersonListingStates.Error
            return
        }
        state.value = PersonListingStates.Loading
        viewModelScope.launch(Dispatchers.Main) {
            if (query.isEmpty()) {
                loadMore()
                return@launch
            }

            state.value = repository.search(query)?.let {
                PersonListingStates.SearchedPersons(it)
            } ?: PersonListingStates.Error
        }
    }
}
