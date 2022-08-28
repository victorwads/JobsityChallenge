package br.com.victorwads.job.vicflix.features.showdetails.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.IOException

class SeasonsRepository(
    private val service: SeasonsService
) {

    suspend fun getSeasons(show: Show): List<Season>? = withContext(IO) {
        try {
            service.getSeasons(show.id).execute().body() ?: listOf()
        } catch (e: IOException) {
            null
        }
    }

    suspend fun getEpisodes(season: Season): List<Episode>? = withContext(IO) {
        try {
            service.getEpisodes(season.id).execute().body() ?: listOf()
        } catch (e: IOException) {
            null
        }
    }

    // TODO Handle End and Errors
}
