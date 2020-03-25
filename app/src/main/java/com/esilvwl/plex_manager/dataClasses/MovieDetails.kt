package com.esilvwl.dataClasses

import com.esilvwl.plex_manager.dataClasses.Genre

//Data class because only used to store data
data class MovieDetails (
    val id : Int,
    val original_title : String,
    val overview : String,
    val release_date : String,
    val poster_path : String,
    val backdrop_path : String,
    val vote_average : Double,
    val vote_count : Int,
    val runtime: Int,
    val genres : List<Genre>
)