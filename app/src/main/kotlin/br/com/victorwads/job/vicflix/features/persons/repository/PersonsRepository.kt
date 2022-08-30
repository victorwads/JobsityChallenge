package br.com.victorwads.job.vicflix.features.persons.repository

import br.com.victorwads.job.vicflix.features.persons.model.Person
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PersonsRepository(private val service: PersonsService) {

    suspend fun search(query: String): List<Person>? = withContext(IO) {
        try {
            service.search(query).execute().body()
                ?.sortedBy { it.score }
                ?.map { it.person }
        } finally {
            // TODO Handle End and Errors
        }
    }
}
