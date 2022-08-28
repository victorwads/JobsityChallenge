package br.com.victorwads.job.vicflix.features.showepisode.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesService {

    @GET("shows/{id}/seasons")
    fun getSeasons(@Path("id") id: Int): Call<List<Episode>>

}