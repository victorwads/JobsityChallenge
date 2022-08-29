package br.com.victorwads.job.vicflix.features.shows.repository

import android.content.Context
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.repositories.model.ShowSearch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ShowsRepository(
    private val service: ShowsService,
    context: Context
) {

    private val cache = ShowsCachedRepository(context)
    var currentPage = 0

    suspend fun search(query: String): List<ShowSearch>? = withContext(IO) {
        try {
            service.search(query).execute().body() ?: listOf()
        } finally {
            // TODO Handle End and Errors
        }
    }

    suspend fun getMoreShows(): List<Show>? = withContext(IO) {
        val page = currentPage
        if (cache.hasPage(page)) {
            cache.getPage(page)?.takeIf { it.isNotEmpty() }?.also {
                currentPage++
                return@withContext it
            }
        }
        try {
            val shows = service.loadPage(page).execute().body()?.also {
                async { cache.savePage(page, it) }
            }
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
