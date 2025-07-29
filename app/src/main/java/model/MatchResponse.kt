package model

import androidx.lifecycle.viewmodel.compose.viewModel

data class MatchResponse(
        val response : List<MatchItem>
)

data class MatchItem(
    val title : String,
    val competition : String,
    val videos : List<Video>,
    val thumbnail : String,
    val date : String,
    val isFavorite: Boolean,

    // مضافة لتجربة الشكل
    val team1Name: String? = "Barcelona",
    val team2Name: String? = "Real Madrid",
    val team1Logo: String? = "https://upload.wikimedia.org/wikipedia/en/4/47/FC_Barcelona_%28crest%29.svg",
    val team2Logo: String? = "https://upload.wikimedia.org/wikipedia/en/5/56/Real_Madrid_CF.svg",
    val matchTime: String? = "22:00",
    val score: String? = null
)





data class Competition(
    val name : String,
    )


data class Video(
    val title: String,
    val embed: String
)


data class LeagueWithMatches(
    val leagueName: String,
    val matches: List<MatchItem>
)
