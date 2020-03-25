package com.esilvwl.dataClasses

import com.esilvwl.plex_manager.dataClasses.Genre
import com.google.gson.annotations.SerializedName

//Data class because only used to store data
data class SerieDetails (
    val id : Int,
    @SerializedName("name")
    val original_title : String,
    val overview : String,
    @SerializedName("first_air_date")
    val release_date : String,
    val poster_path : String,
    val backdrop_path : String,
    val vote_average : Double,
    val number_of_seasons: Int,
    val number_of_episodes: Int,
    val genres : List<Genre>
)