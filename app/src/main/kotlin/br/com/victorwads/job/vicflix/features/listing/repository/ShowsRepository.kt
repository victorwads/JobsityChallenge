package br.com.victorwads.job.vicflix.features.listing.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ShowsRepository(
    val service: ShowsService
) {

    var currentPage = 0

    suspend fun search(query: String): List<Show> = withContext(IO) {
        service.search(query).execute().body() ?: listOf()
    }

    suspend fun getMoreShows(): List<Show>? = withContext(IO) {
        currentPage++
        service.loadPage(currentPage).execute().body()
    }

    // TODO Handle End and Errors
}