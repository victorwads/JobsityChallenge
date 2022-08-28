package br.com.victorwads.job.vicflix.features.showdetails.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeasonsService {

    @GET("shows/{id}/seasons")
    fun getSeasons(@Path("id") id: Int): Call<List<Season>>

    @GET("seasons/{id}/episodes")
    fun getEpisodes(@Path("id") id: Int): Call<List<Episode>>
}
