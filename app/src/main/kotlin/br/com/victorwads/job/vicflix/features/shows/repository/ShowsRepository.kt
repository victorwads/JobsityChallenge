package br.com.victorwads.job.vicflix.features.shows.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.model.ShowSearch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.IOException

class ShowsRepository(
    private val service: ShowsService
) {

    var currentPage = 0

    suspend fun search(query: String): List<ShowSearch>? = withContext(IO) {
        try {
            service.search(query).execute().body() ?: listOf()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun getMoreShows(): List<Show>? = withContext(IO) {
        try {
            val shows = service.loadPage(currentPage).execute().body()
            currentPage++
            shows
        } catch (e: IOException) {
            null
        }
    }

    fun clear() {
        currentPage = 0
    }

    // TODO Handle End and Errors
}
