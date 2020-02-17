package com.theavengers.movieapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theavengers.movieapp.model.ResultList
import java.util.*
import kotlin.collections.ArrayList


class TopMoviesAdapter(result: ArrayList<ResultList.Results>?, val ctx: Context) : RecyclerView.Adapter<TopMoviesAdapter.MyViewHolder>() {

    var resultList1 : ArrayList<ResultList.Results>? = result
    var context : Context = ctx

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList1?.size!!
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w185"+resultList1?.get(position)?.poster_path).into(holder.iv_poster_path)
        holder.tv_original_language.setText(resultList1?.get(position)?.original_language)
        holder.tv_original_title.setText(resultList1?.get(position)?.original_title)
        holder.tv_release_date.setText(resultList1?.get(position)?.release_date)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_poster_path : ImageView = itemView.findViewById(R.id.iv_poster_path)
        var tv_original_title : TextView = itemView.findViewById(R.id.original_title)
        var tv_original_language : TextView = itemView.findViewById(R.id.original_language)
        var tv_release_date : TextView = itemView.findViewById(R.id.release_date)
    }

//    fun filter(charText1: String): ArrayList<ResultList.Results>{
//       var charText = charText1
//        Log.d("Asedd",charText+" resultlistsize "+ resultList?.size+"")
//        charText = charText.toLowerCase()
////        ((context as ViewMovieActivity).resultList?.resultList as ArrayList).clear()
//
//        if (charText.length == 0) {
//            resultList?.addAll((context as ViewMovieActivity).resultList?.resultList as ArrayList)
//        }
//        else{
//            val iter:Iterator<ResultList.Results> = ((context as ViewMovieActivity).resultList?.resultList as ArrayList).iterator()
//            while(iter.hasNext()){
//
//                val results:ResultList.Results = iter.next()
//                Log.d("Original_Title",results.original_title)
//
//                if(results.original_title.toLowerCase().contains(charText)) {
//                    Log.d("Original_Title1", results.original_title)
//                    resultList?.add(results)
//                }
//            }
//        }
//        notifyDataSetChanged()
//        return resultList!!
//    }

    fun filter(charText: String, newResultList: ResultList?):ArrayList<ResultList.Results> {

        Log.d("searched_content",charText)

        var charText = charText
        var resultList2:ResultList? = newResultList as ResultList

        charText = charText.toLowerCase(Locale.getDefault())

//        resultList2?.resultList?.clear()
        if (charText.length == 0) {
            resultList2?.resultList?.let {
                resultList2.resultList!!.addAll(it)
//                (context as ViewMovieActivity).resultList?.resultList?.addAll(
//                    it
//                )
            }
        } else {

//            for (movieInfo in resultList2?.resultList!!) {
//                Log.d("String_Diff",movieInfo.original_title.toLowerCase(Locale.getDefault())+"    "+ charText)
//                if (movieInfo.original_title.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    resultList2?.resultList?.add(movieInfo)
//                    Log.d(movieInfo.original_title,"Original_title")
//                }
//            }
        }
        return resultList2!!.resultList!!
    }

}
