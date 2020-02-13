package com.theavengers.movieapp.model

import com.google.gson.annotations.SerializedName

class ResultList {

    @SerializedName("results")
    public val resultList:List<Results>? = null;

    class Results{

        @SerializedName("popularity")
        val popularity:Double = 0.0

        @SerializedName("vote_count")
        val vote_count:Int = 0

        @SerializedName("video")
        val video:Boolean = false

        @SerializedName("poster_path")
        val poster_path:String = ""

        @SerializedName("id")
        val id:Int = 0

        @SerializedName("adult")
        val adult:Boolean = false

        @SerializedName("backdrop_path")
        val backdrop_path:String = ""

        @SerializedName("original_language")
        public val original_language:String = ""

        @SerializedName("original_title")
        public val original_title:String = ""

        @SerializedName("vote_average")
        val vote_average:Double = 0.0

        @SerializedName("overview")
        val overview:String = ""

        @SerializedName("release_date")
        val release_date:String = ""

    }

}
