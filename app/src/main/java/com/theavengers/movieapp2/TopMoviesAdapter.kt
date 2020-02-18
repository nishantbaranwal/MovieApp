package com.theavengers.movieapp2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theavengers.movieapp2.R
import com.theavengers.movieapp2.model.ResultList
import java.io.Serializable
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
        holder.cardView.setOnClickListener(View.OnClickListener {

            val intent = Intent(context,PlayerActivity::class.java)
//            val passData:ResultList.Results = resultList1!!.get(position)
            intent.putExtra("passedData",resultList1?.get(position))
            context.startActivity(intent)

        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView = itemView.findViewById<CardView>(R.id.cardView)
        var iv_poster_path : ImageView = itemView.findViewById(R.id.iv_poster_path)
        var tv_original_title : TextView = itemView.findViewById(R.id.original_title)
        var tv_original_language : TextView = itemView.findViewById(R.id.original_language)
        var tv_release_date : TextView = itemView.findViewById(R.id.release_date)
    }



    fun filter(searchText: String, newResultList: ResultList): ArrayList<ResultList.Results> {
        var charText = searchText
        var resultList2: ResultList =
            ResultList()
        resultList2 = newResultList
        charText = charText.toLowerCase(Locale.getDefault())

        if (charText.length == 0) {
            resultList2.resultList.let {
                if (it != null) {
                    resultList2.resultList?.addAll(it)
                }
            }
            return resultList2.resultList!!
        } else {
            val returnValue: ArrayList<ResultList.Results>? = arrayListOf()

            for (movieInfo in resultList2.resultList!!) {
                if (movieInfo.original_title.toLowerCase(Locale.getDefault()).contains(charText)) {
                    returnValue?.add(movieInfo)
                }
            }
            return returnValue!!
        }
    }

}
