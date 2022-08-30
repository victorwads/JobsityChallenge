package br.com.victorwads.job.vicflix.features.persons.model

import android.os.Parcelable
import br.com.victorwads.job.vicflix.commons.repositories.model.APIImage
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: APIImage?,
) : Parcelable
