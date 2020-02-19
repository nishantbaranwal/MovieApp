package com.theavengers.movieapp2.repository.api

import com.theavengers.movieapp2.model.ResultList
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApiInterface {

    @GET("discover/movie?sort_by=popularity.desc&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getResultList(@Query("page") page: Int): Single<Response<ResultList>>

    @GET("discover/movie?certification_country=US&certification.lte=G&sort_by=popularity.desc&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getPopularKidsMovies(@Query("page") page: Int): Single<Response<ResultList>>

    @GET("discover/movie?with_genres=18&primary_release_year=2014&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getYearTopDramas(@Query("page") page: Int): Single<Response<ResultList>>

    @GET("/3/search/movie")
    fun searchData(@Query("query") query: String, @Query("api_key") api_key: String,
                   @Query("page") page: Int): Single<Response<ResultList>>

}