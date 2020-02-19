package com.theavengers.movieapp2.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.theavengers.movieapp2.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {

    var disposable:Disposable? = null

    override fun onResume() {
        super.onResume()
        disposable = Observable.timer(2,TimeUnit.SECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                val intent = Intent(applicationContext,
                    LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }
}