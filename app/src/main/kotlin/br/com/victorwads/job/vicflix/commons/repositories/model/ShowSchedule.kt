package br.com.victorwads.job.vicflix.commons.repositories.model

import com.google.gson.annotations.SerializedName

data class ShowSchedule(
    @SerializedName("time")
    val time: String,
    @SerializedName("days")
    val days: List<String>,
)
