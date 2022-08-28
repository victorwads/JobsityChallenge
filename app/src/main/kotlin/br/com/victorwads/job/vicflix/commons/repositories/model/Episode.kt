package br.com.victorwads.job.vicflix.commons.repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("season") val season: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("image") val image: APIImage?,
    @SerializedName("summary") val summary: String?,
) : Parcelable