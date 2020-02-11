package com.theavengers.movieapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theavengers.movieapp.model.ResultList

class TopMoviesAdapter(
    result: List<ResultList.Results>?,
    ctx: Context
) :
    RecyclerView.Adapter<TopMoviesAdapter.MyViewHolder>() {

    var resultList : List<ResultList.Results>? = result
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
        return resultList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w185"+resultList?.get(position)?.poster_path).into(holder.iv_poster_path)
        holder.tv_original_language.setText(resultList?.get(position)?.original_language)
        holder.tv_original_title.setText(resultList?.get(position)?.original_title)
        holder.tv_release_date.setText(resultList?.get(position)?.release_date)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_poster_path : ImageView = itemView.findViewById(R.id.iv_poster_path)
        var tv_original_title : TextView = itemView.findViewById(R.id.original_title)
        var tv_original_language : TextView = itemView.findViewById(R.id.original_language)
        var tv_release_date : TextView = itemView.findViewById(R.id.release_date)
    }
}
