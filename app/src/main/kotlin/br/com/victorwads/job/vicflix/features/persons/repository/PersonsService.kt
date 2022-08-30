package br.com.victorwads.job.vicflix.features.persons.repository

import br.com.victorwads.job.vicflix.features.persons.model.PersonSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonsService {

    @GET("search/people")
    fun search(@Query("q") query: String): Call<List<PersonSearch>>
}
