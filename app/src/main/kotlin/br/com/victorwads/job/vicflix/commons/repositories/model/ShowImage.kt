package br.com.victorwads.job.vicflix.commons.repositories.model

import com.google.gson.annotations.SerializedName

data class ShowImage(
    //"image": {
    @SerializedName("medium")
    val medium: String?,
//    "medium": "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
    @SerializedName("original")
    val original: String?,
//    "original": "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
//},

)
