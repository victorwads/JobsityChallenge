package br.com.victorwads.job.vicflix.commons.repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowImage(
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("original")
    val original: String?,
) : Parcelable
