package com.theavengers.movieapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.theavengers.movieapp.model.ResultList
import com.theavengers.movieapp.model.TheMovieDbApiInterface
import com.theavengers.movieapp.model.getRetrofit
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ViewMovieActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var resultList: ResultList?= null
    var disposable: Disposable?= null
    var adapter : TopMoviesAdapter ?= null
    override fun onQueryTextSubmit(query: String?): Boolean {
        for(results : ResultList.Results in resultList?.resultList!!) {
            if (results.original_title.equals(query)) {
                resultList?.let { updateUI(it) }
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { adapter?.filter(it) }
        resultList?.let { updateUI(it) }

        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        val viewPager:ViewPager = findViewById(R.id.viewPager)
        val tabLayout:TabLayout = findViewById(R.id.tabLayout)
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        val searchView:SearchView = findViewById(R.id.search_view)
        val theMovieDbApiInterface : TheMovieDbApiInterface? = getRetrofit()?.
            create(TheMovieDbApiInterface::class.java)
        val responseSingleResultList : Single<Response<ResultList>>? = theMovieDbApiInterface?.
            getPopularKidsMovies()
        val progressDialog = ProgressDialog(this)

        searchView.setOnQueryTextListener(this)


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
//                resultList?.let { updateUI(it) };
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

        viewPager.setAdapter(viewPagerAdapter)
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun updateUI(resultList: ResultList) {
        val recyclerView: RecyclerView? = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)

        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setHasFixedSize(true)

        adapter = TopMoviesAdapter(resultList.resultList,this)
        recyclerView?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
