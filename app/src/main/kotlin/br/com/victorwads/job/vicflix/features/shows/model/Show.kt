package br.com.victorwads.job.vicflix.features.shows.model

import android.os.Parcelable
import br.com.victorwads.job.vicflix.commons.repositories.model.APIImage
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String?,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("genres") val genres: List<String>?,
    @SerializedName("status") val status: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("averageRuntime") val averageRuntime: Int?,
    @SerializedName("officialSite") val officialSite: String?,
    @SerializedName("schedule") val schedule: ShowSchedule?,
    @SerializedName("image") val image: APIImage?,
    @SerializedName("summary") val summary: String?,
) : Parcelable
