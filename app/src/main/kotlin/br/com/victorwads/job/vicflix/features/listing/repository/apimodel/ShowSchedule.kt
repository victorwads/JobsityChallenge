package br.com.victorwads.job.vicflix.features.listing.repository.apimodel

import com.google.gson.annotations.SerializedName

data class ShowSchedule(
    @SerializedName("time")
    val time: String,
//"time": "22:00"
    @SerializedName("days")
    val days: String,
//"days": [
//  "Thursday"
//]
)
