package br.com.victorwads.job.vicflix.commons.repositories.model

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("averageRuntime")
    val averageRuntime: Int,
    @SerializedName("officialSite")
    val officialSite: String,
    @SerializedName("schedule")
    val schedule: ShowSchedule,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("image")
    val image: ShowImage?,
    @SerializedName("summary")
    val summary: String,
)