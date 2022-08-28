package br.com.victorwads.job.vicflix.commons.repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
    @SerializedName("episodeOrder") val size: String,
    @SerializedName("image") val image: APIImage?,
    @SerializedName("summary") val summary: String?,
) : Parcelable
