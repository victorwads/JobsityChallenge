package br.com.victorwads.job.vicflix.features.shows.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.model.ShowSearch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ShowsRepository(
    private val service: ShowsService
) {

    var currentPage = 0

    suspend fun search(query: String): List<ShowSearch> = withContext(IO) {
        service.search(query).execute().body() ?: listOf()
    }

    suspend fun getMoreShows(): List<Show>? = withContext(IO) {
        currentPage++
        service.loadPage(currentPage).execute().body()
    }

    fun clear() {
        currentPage = 0
    }

    // TODO Handle End and Errors
}