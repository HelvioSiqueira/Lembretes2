package com.example.lembretes2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lembretes2.auth.AuthMananger
import com.example.lembretes2.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private val authManager: AuthMananger by inject()

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                handleSignInResult(it.data)
            }
        }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener { signIn() }
        checkGooglePlayServices()

    }

    private fun signIn() {
        val signInIntent = authManager.getSignInIntent()
        register.launch(signInIntent)
    }

    private fun handleSignInResult(intent: Intent?) {
        try {
            GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.error_login_failed, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkGooglePlayServices(){
        val api = GoogleApiAvailability.getInstance()

        val resultCode = api.isGooglePlayServicesAvailable(this)
        if(resultCode != ConnectionResult.SUCCESS){
            if(api.isUserResolvableError(resultCode)){
                val dialog = api.getErrorDialog(this, resultCode, REQUEST_PLAY_SERVICES)

                dialog?.setOnCancelListener{
                    finish()
                }
                dialog?.show()
            } else {
                Toast.makeText(this, R.string.error_play_services_not_supported, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    companion object {
        private const val REQUEST_SIGN_IN = 1000
        private const val REQUEST_PLAY_SERVICES = 2000
    }
}