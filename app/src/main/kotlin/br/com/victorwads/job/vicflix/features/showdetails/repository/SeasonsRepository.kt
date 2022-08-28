package br.com.victorwads.job.vicflix.features.showdetails.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SeasonsRepository(
    private val service: SeasonsService
) {

    suspend fun getSeasons(show: Show): List<Season> = withContext(IO) {
        service.getSeasons(show.id).execute().body() ?: listOf()
    }

    // TODO Handle End and Errors
}