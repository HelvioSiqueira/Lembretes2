package com.example.lembretes2.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

open class AuthMananger(context: Context) : Auth {
    private val appContext = context.applicationContext
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, gso)
    }

    fun getUserAccount() = GoogleSignIn.getLastSignedInAccount(appContext)

    override fun getUserId() = getUserAccount()?.id

    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    fun singOut(callback: () -> Unit) {
        googleSignInClient.signOut()?.addOnCanceledListener {
            callback()
        }
    }
}