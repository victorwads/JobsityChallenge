package br.com.victorwads.job.vicflix.features.listing.repository

import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowsService {

    @GET("search/shows")
    fun search(
        @Query("q")
        query: String
    ): Call<List<Show>>

    @GET("shows")
    fun loadPage(
        @Query("page")
        page: Int
    ): Call<List<Show>>
}