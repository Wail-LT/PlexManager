package com.esilvwl.plexapi

import com.esilvwl.dataClasses.MovieDetails
import com.esilvwl.dataClasses.ItemList
import com.esilvwl.dataClasses.SerieDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MdbService {

    //Movie
    @GET("movie/{id}?api_key=621917fb16dec0739798fbfbaf73372b")
    fun movieDetails(@Path("id") id: Int): Call<MovieDetails>;

    @GET("search/movie?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr&include_adult=false")
    fun searchMovie(@Query("query") title: String, @Query("page") page: Int ): Call<ItemList>;

    @GET("movie/{movie_id}/similar?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr-FR&page=1")
    fun similarMovie(@Path("movie_id") id: Int): Call<ItemList>;


    @GET("discover/movie?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr-FR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    fun mostPopularMovies(@Query("with_genres") genre: Int): Call<ItemList>;



    //Serie
    @GET("tv/{id}?api_key=621917fb16dec0739798fbfbaf73372b")
    fun serieDetails(@Path("id") id: Int): Call<SerieDetails>;

    @GET("discover/tv?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr-FR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    fun mostPopularSeries(@Query("with_genres") genre: Int): Call<ItemList>;

    @GET("tv/{serie_id}/similar?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr-FR&page=1")
    fun similarSerie(@Path("serie_id") id: Int): Call<ItemList>;

    @GET("search/tv?api_key=621917fb16dec0739798fbfbaf73372b&language=en-US%2C%20fr&include_adult=false")
    fun searchSerie(@Query("query") title: String, @Query("page") page: Int ): Call<ItemList>;




}