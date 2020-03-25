package com.esilvwl.plexmanager.api

import android.util.Log
import com.esilvwl.dataClasses.MovieDetails
import com.esilvwl.plexmanager.enums.ECategories
import com.esilvwl.dataClasses.ItemList
import com.esilvwl.dataClasses.SerieDetails
import com.esilvwl.plexapi.MdbService
import com.esilvwl.plexmanager.enums.EItemTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    companion object Static {
        val imageBaseUrl = "https://image.tmdb.org/t/p"
        val posterSize = "/w92"
        val backdropSize = "/w300"
        //For Debugging
        private val TAG = Api::class.simpleName

        //Api Url
        private val _tanUrl = "https://api.themoviedb.org/3/"


        //Retrofit INIT
        private var _retrofit = Retrofit.Builder()
            .baseUrl(_tanUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Bind Interface services to Retrofit
        private var _mdbService: MdbService = _retrofit.create(MdbService::class.java)

        fun getMovieDetails(movieId: Int, callback: (MovieDetails) -> Unit) {

            _mdbService.movieDetails(movieId).enqueue(object : Callback<MovieDetails> {
                override fun onResponse( call: Call<MovieDetails>, response: Response<MovieDetails> ) {
                    if (response.isSuccessful) {
                        var movieDetails = response.body()

                        //Init Views
                        callback(movieDetails!!)
                    } else
                        Log.d(TAG, "getMovieDetails Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getMovieDetails Request Failed :\n $t")
                }
            })
        }

        fun getMoviesByGenre(category: ECategories, callback: (ItemList?) -> Unit) {

            _mdbService.mostPopularMovies(category.categoryId).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var movieList = response.body()
                        if (movieList != null)
                        {
                            movieList.itemList.forEach { it.type=EItemTypes.Movie }
                            movieList.category = category
                        }

                        //Init Views
                        callback(movieList)
                    } else
                        Log.d(TAG, "getPopularMovies Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getPopularMovies Request Failed :\n $t")
                }
            })
        }

        fun getSimilarMovie(movieId: Int, callback: (ItemList?) -> Unit) {
            _mdbService.similarMovie(movieId).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var movieList = response.body()
                        if(movieList!=null)
                            movieList.itemList.forEach { it.type=EItemTypes.Movie }

                        callback(movieList)
                    } else
                        Log.d(TAG, "getSimilarMovie Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getSimilarMovie Request Failed :\n $t")
                }
            })
        }

        fun searchMovie(title: String, page: Int = 1, callback: (ItemList?) -> Unit) {
            _mdbService.searchMovie(title, page).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var movieList = response.body()
                        if(movieList!=null)
                            movieList.itemList.forEach { it.type=EItemTypes.Movie }

                        callback(movieList)
                    } else
                        Log.d(TAG, "searchMovie Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "searchMovie Request Failed :\n $t")
                }
            })
        }





        fun getSerieDetails(serieId: Int, callback: (SerieDetails) -> Unit) {

            _mdbService.serieDetails(serieId).enqueue(object : Callback<SerieDetails> {
                override fun onResponse( call: Call<SerieDetails>, response: Response<SerieDetails> ) {
                    if (response.isSuccessful) {
                        var serieDetails = response.body()

                        //Init Views
                        callback(serieDetails!!)
                    } else
                        Log.d(TAG, "getSerieDetails Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<SerieDetails>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getSerieDetails Request Failed :\n $t")
                }
            })
        }

        fun getSerieByGenre(categories: ECategories, callback: (ItemList?) -> Unit) {
            _mdbService.mostPopularSeries(categories.categoryId).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var serieList = response.body()

                        if (serieList != null)
                        {
                            serieList.category = categories
                            serieList.itemList.forEach { it.type=EItemTypes.Serie }
                        }

                        //Init Views
                        callback(serieList)
                    } else
                        Log.d(TAG, "getSerieByGenre Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getSerieByGenre Request Failed :\n $t")
                }
            })
        }

        fun getSimilarSerie(serieId: Int, callback: (ItemList?) -> Unit) {
            _mdbService.similarSerie(serieId).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var serieList = response.body()
                        if(serieList!=null)
                            serieList.itemList.forEach { it.type=EItemTypes.Serie }

                        callback(serieList)
                    } else
                        Log.d(TAG, "getSimilarSerie Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "getSimilarSerie Request Failed :\n $t")
                }
            })
        }

        fun searchSerie(title: String, page: Int = 1, callback: (ItemList?) -> Unit) {
            _mdbService.searchSerie(title, page).enqueue(object : Callback<ItemList> {
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    if (response.isSuccessful) {
                        var serieList = response.body()
                        //Init Views
                        if(serieList != null)
                            serieList.itemList.forEach { it.type=EItemTypes.Serie }

                        callback(serieList)
                    } else
                        Log.d(TAG, "searchSerie Request Failed:\n ${response.code()}")
                }

                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //Log error
                    Log.d(TAG, "searchSerie Request Failed :\n $t")
                }
            })
        }


    }
}