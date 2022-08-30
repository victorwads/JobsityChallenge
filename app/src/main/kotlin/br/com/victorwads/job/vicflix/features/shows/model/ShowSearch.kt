package br.com.victorwads.job.vicflix.features.shows.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowSearch(
    @SerializedName("show") val show: Show,
    @SerializedName("score") val score: Float,
) : Parcelable
