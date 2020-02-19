package com.theavengers.movieapp2.view.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.theavengers.movieapp2.R
import com.theavengers.movieapp2.model.ResultList
import com.theavengers.movieapp2.repository.api.TheMovieDbApiInterface
import com.theavengers.movieapp2.viewmodel.MoviesAdapter
import com.theavengers.movieapp2.viewmodel.ViewPagerAdapter
import com.theavengers.movieapp2.viewmodel.getRetrofit
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class ViewMovieActivity : AppCompatActivity(),SearchView.OnQueryTextListener{

    var resultList: ResultList?= ResultList()
    var newDataList: ResultList?= ResultList()

    var disposable: Disposable?= null
    var adapter : MoviesAdapter?= null

    var isScrolling: Boolean = false
    var currentItems:Int = 0
    var totalItems:Int = 0
    var scrollItems:Int = 0
    var text:String = ""
    var pageNumber : Int = 1
    var itemCount : Int = 10
    var recyclerView: RecyclerView?= null
    var layoutManager: LinearLayoutManager?= null
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        text = newText!!
        if (newText.length > 0) {
            searchWithText(newText,false)
        }
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)
        val searchView:SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(this@ViewMovieActivity)

        val viewPager:ViewPager = findViewById(R.id.viewPager)
        val tabLayout:TabLayout = findViewById(R.id.tabLayout)
        val viewPagerAdapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager
            )
        recyclerView  = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setHasFixedSize(true)
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
        viewPager.setAdapter(viewPagerAdapter)
        viewPager.setCurrentItem(0)
        tabLayout.setupWithViewPager(viewPager)

    }


    fun searchWithText(searchKey:String, isPagination:Boolean) {

        val theMovieDbApiInterface : TheMovieDbApiInterface? = getRetrofit()
            ?.create(TheMovieDbApiInterface::class.java)
        if(!isPagination) {
            val responseSingleResultList: Single<Response<ResultList>>? =
                theMovieDbApiInterface?.searchData(searchKey, "bfe85bf7d7aac066e48cfa121ec821cc", pageNumber)
            val progressDialog = ProgressDialog(this)
            progressDialog.max = 100
            progressDialog.setMessage("Its loading....")
            progressDialog.setTitle("Downloading")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressDialog.show()

            responseSingleResultList?.subscribeOn(Schedulers.io())?.observeOn(
                AndroidSchedulers.mainThread()
            )?.subscribe(object : SingleObserver<Response<ResultList>> {

                override fun onSuccess(t: Response<ResultList>) {
                    resultList = t.body();
                    adapter =
                        MoviesAdapter(
                           resultList?.resultList!!,this@ViewMovieActivity
                        )
                    recyclerView?.adapter = adapter
//                    adapter?.addNewData(resultList?.resultList)

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
        else{

            pageNumber++
            val responseSingleResultList: Single<Response<ResultList>>? =
                theMovieDbApiInterface?.searchData(searchKey, "bfe85bf7d7aac066e48cfa121ec821cc", pageNumber)
            val progressDialog = ProgressDialog(this)
            progressDialog.max = 100
            progressDialog.setMessage("Its loading....")
            progressDialog.setTitle("Downloading")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
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
    }

    private fun fetchData() {
        searchWithText(text,true)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
