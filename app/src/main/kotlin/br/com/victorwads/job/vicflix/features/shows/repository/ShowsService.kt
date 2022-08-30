package br.com.victorwads.job.vicflix.features.shows.repository

import br.com.victorwads.job.vicflix.features.shows.model.Show
import br.com.victorwads.job.vicflix.features.shows.model.ShowSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowsService {

    @GET("search/shows")
    fun search(@Query("q") query: String): Call<List<ShowSearch>>

    @GET("shows")
    fun loadPage(@Query("page") page: Int): Call<List<Show>>
}
