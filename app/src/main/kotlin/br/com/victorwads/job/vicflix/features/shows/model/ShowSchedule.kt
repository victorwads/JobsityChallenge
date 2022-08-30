package br.com.victorwads.job.vicflix.features.shows.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowSchedule(
    @SerializedName("time") val time: String?,
    @SerializedName("days") val days: List<String>?,
) : Parcelable
