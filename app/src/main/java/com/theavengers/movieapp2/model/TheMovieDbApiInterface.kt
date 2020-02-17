package com.theavengers.movieapp2.model

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface TheMovieDbApiInterface {

    @GET("discover/movie?sort_by=popularity.desc&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getResultList(): Single<Response<ResultList>>

    @GET("discover/movie?certification_country=US&certification.lte=G&sort_by=popularity.desc&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getPopularKidsMovies(): Single<Response<ResultList>>

    @GET("discover/movie?with_genres=18&primary_release_year=2014&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getYearTopDramas(): Single<Response<ResultList>>
}