package br.com.victorwads.job.vicflix.commons.repositories.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    val instance: Retrofit
        get() = retrofit ?: initInstance()

    private var retrofit: Retrofit? = null

    private fun initInstance(): Retrofit = with(Retrofit.Builder()) {
        baseUrl(API_BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    private const val API_BASE_URL = "https://api.tvmaze.com/"
}
