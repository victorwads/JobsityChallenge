package br.com.victorwads.job.vicflix.features.persons.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonSearch(
    @SerializedName("person") val person: Person,
    @SerializedName("score") val score: Float,
) : Parcelable
