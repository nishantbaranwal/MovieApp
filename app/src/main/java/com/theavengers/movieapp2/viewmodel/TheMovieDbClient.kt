package com.theavengers.movieapp2.viewmodel

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


fun getRetrofit(): Retrofit? {
    var interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    var okHttpClient : OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    var retrofit = Retrofit.Builder().client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.themoviedb.org/3/").build()
    return retrofit
}