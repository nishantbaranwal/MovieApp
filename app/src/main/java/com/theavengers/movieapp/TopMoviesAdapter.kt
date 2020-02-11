package com.theavengers.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theavengers.movieapp.model.ResultList

class TopMoviesAdapter(result: ResultList.Results) : RecyclerView.Adapter<TopMoviesAdapter.MyViewHolder>() {
    var view:View? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopMoviesAdapter.MyViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout,parent,false)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TopMoviesAdapter.MyViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MyViewHolder{

    }
}
