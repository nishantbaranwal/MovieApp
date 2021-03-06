package com.theavengers.movieapp2.view.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theavengers.movieapp2.R
import com.theavengers.movieapp2.model.ResultList
import com.theavengers.movieapp2.repository.api.TheMovieDbApiInterface
import com.theavengers.movieapp2.viewmodel.getRetrofit
import com.theavengers.movieapp2.viewmodel.MoviesAdapter
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class YearBestMoviesFragment(context: Context) : Fragment() {

    var ctx: Context = context
    var disposable: Disposable? = null
    var resultList: ResultList? = null
    var fragmentView: View? = null
    var isScrolling: Boolean = false
    var currentItems:Int = 0
    var totalItems:Int = 0
    var scrollItems:Int = 0
    var pageNumber : Int = 1
    var newDataList: ResultList?= ResultList()
    var adapter:MoviesAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_top_movies, container, false)

        val theMovieDbApiInterface: TheMovieDbApiInterface? =
            getRetrofit()
                ?.create(TheMovieDbApiInterface::class.java)
        val responseSingleResultList: Single<Response<ResultList>>? =
            theMovieDbApiInterface?.getYearTopDramas(pageNumber)
        val progressDialog = ProgressDialog(ctx)

        progressDialog.max = 100
        progressDialog.setMessage("Its loading....")
        progressDialog.setTitle("Downloading")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.show()

        responseSingleResultList?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread()
        )?.subscribe(object : SingleObserver<Response<ResultList>> {

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
                Log.d("ConnectionUnsuccessfull", e.message)
            }

        })
        return fragmentView
    }

    fun updateUI(resultList: ResultList){
        val recyclerView: RecyclerView? = fragmentView?.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(ctx)

        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setHasFixedSize(true)

        adapter = MoviesAdapter(
            resultList.resultList as ArrayList<ResultList.Results>,
            ctx
        )
        recyclerView?.adapter = adapter


        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }

            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                currentItems = layoutManager?.childCount!!
                totalItems = layoutManager?.itemCount!!
                scrollItems = layoutManager?.findFirstVisibleItemPosition()!!
                if(isScrolling && (currentItems+scrollItems) == totalItems){
                    isScrolling = false
                    fetchData()
                }
            }
        })


    }

    private fun fetchData() {
        pageNumber++
        val theMovieDbApiInterface : TheMovieDbApiInterface? = getRetrofit()
            ?.
                create(TheMovieDbApiInterface::class.java)
        val responseSingleResultList : Single<Response<ResultList>>? = theMovieDbApiInterface?.
            getYearTopDramas(pageNumber)
        val progressDialog = ProgressDialog(ctx)
        progressDialog.max = 100
        progressDialog.setMessage("Its loading....")
        progressDialog.setTitle("Downloading")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.show()

        responseSingleResultList?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread()
        )?.subscribe(object : SingleObserver<Response<ResultList>> {

            override fun onSuccess(t: Response<ResultList>) {

                newDataList = t.body();
                resultList?.resultList?.addAll(newDataList?.resultList!!)
                Log.d("SizeASDAD",resultList?.resultList?.size.toString())
                adapter?.addNewData(newDataList?.resultList)
                progressDialog.dismiss()
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                progressDialog.dismiss()
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}