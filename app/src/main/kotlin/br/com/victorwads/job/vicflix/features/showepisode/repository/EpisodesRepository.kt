package br.com.victorwads.job.vicflix.features.showepisode.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class EpisodesRepository(
    private val service: EpisodesService
) {

    suspend fun getSeasons(show: Episode): List<Episode> = withContext(IO) {
        service.getSeasons(show.id).execute().body() ?: listOf()
    }

    // TODO Handle End and Errors
}