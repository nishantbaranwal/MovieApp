package com.theavengers.movieapp

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theavengers.movieapp.model.ResultList
import com.theavengers.movieapp.model.TheMovieDbApiInterface
import com.theavengers.movieapp.model.getRetrofit
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class TopMoviesFragment(context: Context) : Fragment() {

    var ctx:Context = context
    var disposable:Disposable ?= null
    var resultList:ResultList ?= null
    var fragmentView:View ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_top_movies,container,false)

        val theMovieDbApiInterface : TheMovieDbApiInterface? = getRetrofit()?.
            create(TheMovieDbApiInterface::class.java)
        val responseSingleResultList : Single<Response<ResultList>>? = theMovieDbApiInterface?.
            getResultList()
        val progressDialog = ProgressDialog(ctx)

        progressDialog.max = 100
        progressDialog.setMessage("Its loading....")
        progressDialog.setTitle("Downloading")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.show()

        responseSingleResultList?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.
                mainThread())?.subscribe(object : SingleObserver<Response<ResultList>> {

            override fun onSuccess(t: Response<ResultList>) {
                Log.d("IsConnectionSuccessfull", (t.code().toString()));
                resultList = t.body();
                resultList?.let { updateUI(it) };
                progressDialog.dismiss()
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d;
            }

            override fun onError(e: Throwable) {
                progressDialog.dismiss()
                Log.d("ConnectionUnsuccessfull",e.message)
            }

        })
        return fragmentView
    }

    fun updateUI(resultList: ResultList){
            val recyclerView: RecyclerView? = fragmentView?.findViewById(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(ctx)

            recyclerView?.setLayoutManager(layoutManager)
            recyclerView?.setHasFixedSize(true)

            val adapter = TopMoviesAdapter(resultList.resultList,ctx)
            recyclerView?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
