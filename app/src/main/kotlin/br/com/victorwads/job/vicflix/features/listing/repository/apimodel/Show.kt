package br.com.victorwads.job.vicflix.features.listing.repository.apimodel

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
//"name": "Under the Dome",
    @SerializedName("type")
    val type: String,
//"type": "Scripted",
    @SerializedName("language")
    val language: String,
//"language": "English",
    @SerializedName("genres")
    val genres: List<String>,
//"genres": [],
    @SerializedName("status")
    val status: String,
//"status": "Ended",
    @SerializedName("runtime")
    val runtime: Int,
//"runtime": 60,
    @SerializedName("averageRuntime")
    val averageRuntime: Int,
//"averageRuntime": 60,
//@SerializedName("premiered")
//val premiered: String,
////"premiered": "2013-06-24",
//@SerializedName("ended")
//val ended: String,
////"ended": "2015-09-10",
    @SerializedName("officialSite")
    val officialSite: String,
//"officialSite": "http://www.cbs.com/shows/under-the-dome/",
@SerializedName("schedule")
val schedule: ShowSchedule,
//"schedule": {
//    "time": "22:00",
//    "days": [
//    "Thursday"
//    ]
//},
//    @SerializedName("rating")
//    val rating: String,
//"rating": {
//    "average": 6.5
//},
    @SerializedName("weight")
    val weight: Int,
//"weight": 98,
//    @SerializedName("network")
//    val network: String,
//"network": {
//    "id": 2,
//    "name": "CBS",
//    "country": {
//        "name": "United States",
//        "code": "US",
//        "timezone": "America/New_York"
//    },
//    "officialSite": "https://www.cbs.com/"
//},
//    @SerializedName("webChannel")
//    val webChannel: String,
//"webChannel": null,
//    @SerializedName("dvdCountry")
//    val dvdCountry: String,
//"dvdCountry": null,
//    @SerializedName("externals")
//    val externals: String,
//"externals": {
//    "tvrage": 25988,
//    "thetvdb": 264492,
//    "imdb": "tt1553656"
//},
    @SerializedName("image")
    val image: ShowImage?,
    @SerializedName("summary")
    val summary: String,
//"summary": "<p><b>Under the Dome</b> is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.</p>",
//    @SerializedName("updated")
//    val updated: String,
//"updated": 1631010933,
)