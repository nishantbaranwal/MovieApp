package com.theavengers.movieapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(),View.OnClickListener {

    var mGoogleSignInClient:GoogleSignInClient ?= null
    var RC_SIGN_IN:Int = 101
    var TAG = "LoginActivity"
    private lateinit var auth: FirebaseAuth
    lateinit var email_et  :EditText
    lateinit var password_et  :EditText
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "login_shared_preference"
    lateinit var sharedPref  :SharedPreferences
    lateinit var editor  :SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPref.edit()

        FirebaseApp.initializeApp(this);
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("486358761316-l0ibf1uqc97gvbk57hmthvj2ccndltdk.apps.googleusercontent.com")
            .requestEmail()
            .build()
        auth = FirebaseAuth.getInstance()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInButton :SignInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this)

        email_et = findViewById<EditText>(R.id.et_email)
        password_et = findViewById<EditText>(R.id.et_password)
        val login_btn = findViewById<Button>(R.id.btn_login)
        login_btn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null || sharedPref.getBoolean("loginned", false)){
            startActivity(Intent(applicationContext,
                ViewMovieActivity::class.java))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.sign_in_button -> signIn()
            R.id.btn_login -> offlineSignIn()
        }
    }

    private fun offlineSignIn() {
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email_et.text.toString()).matches()) {
            if (et_email.text.toString().equals("nishant@gmail.com") && password_et.text.toString().equals("1234567890")) {
                editor.putBoolean("loginned", true)
                editor.apply()
                editor.commit()
                startActivity(
                    Intent(
                        applicationContext,
                        ViewMovieActivity::class.java
                    )
                )
            }
        }
        else
            Toast.makeText(this,"Wrong Email Id Or Password",Toast.LENGTH_LONG).show()

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            startActivity(Intent(applicationContext,
                ViewMovieActivity::class.java))
            val account: GoogleSignInAccount? = task.getResult(ApiException:: class.java)
            firebaseAuthWithGoogle(account!!)
        }
        catch (e:ApiException){
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(applicationContext,
                        ViewMovieActivity::class.java))
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }

            }
    }
}
