package com.theavengers.movieapp.model

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface TheMovieDbApiInterface {

    @GET("discover/movie?sort_by=popularity.desc&api_key=bfe85bf7d7aac066e48cfa121ec821cc")
    fun getResultList(): Single<Response<ResultList>>
}