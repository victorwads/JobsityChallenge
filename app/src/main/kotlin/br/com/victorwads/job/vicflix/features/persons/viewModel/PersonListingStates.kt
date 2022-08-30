package br.com.victorwads.job.vicflix.features.persons.viewModel

import br.com.victorwads.job.vicflix.features.persons.model.Person

sealed class PersonListingStates {
    data class SearchedPersons(val persons: List<Person>) : PersonListingStates()
    object Error : PersonListingStates()
    object Loading : PersonListingStates()
}
