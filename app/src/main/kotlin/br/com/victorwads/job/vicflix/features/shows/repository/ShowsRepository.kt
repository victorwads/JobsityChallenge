package br.com.victorwads.job.vicflix.features.shows.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.model.ShowSearch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ShowsRepository(
    private val service: ShowsService
) {

    var currentPage = 0

    suspend fun search(query: String): List<ShowSearch>? = withContext(IO) {
        try {
            service.search(query).execute().body() ?: listOf()
        } finally {
            // TODO Handle End and Errors
        }
    }

    suspend fun getMoreShows(): List<Show>? = withContext(IO) {
        try {
            val shows = service.loadPage(currentPage).execute().body()
            currentPage++
            shows
        } finally {
            // TODO Handle End and Errors
        }
    }

    fun clear() {
        currentPage = 0
    }
}
