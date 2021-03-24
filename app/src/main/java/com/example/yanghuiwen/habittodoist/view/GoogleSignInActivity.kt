package com.example.yanghuiwen.habittodoist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_google_sign_in.*

class GoogleSignInActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GoogleSignInActivity"
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        val signOutButton =findViewById<Button>(R.id.signOutButton)
        signOutButton .setOnClickListener {
            signOut()
        }
        val signInButton =findViewById<Button>(R.id.signInButton)
        signInButton .setOnClickListener {
            signIn()
        }

        if (auth.currentUser != null){
            accountName.text = auth.currentUser?.email.toString()
            signInButton.visibility = View.GONE
            signOutButton.visibility = View.VISIBLE
        }else{
            signInButton.visibility = View.VISIBLE
            signOutButton.visibility = View.GONE

        }


    }
//
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
                accountName.text = account.email
                signInButton.visibility = View.GONE
                signOutButton.visibility = View.VISIBLE
                var bundle= Bundle()
                bundle.putInt("pageNum",3)
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
                //Log.i(TAG,AllItemData.auth.toString())
               // startActivity( Intent(this, MainActivity::class.java))
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
       // showProgressBar()
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        AllItemData.auth = auth
                        Log.i(TAG,AllItemData.auth.currentUser!!.email.toString())
                        val user = auth.currentUser
                       // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        // [START_EXCLUDE]
                      //  val view = binding.mainLayout
                        // [END_EXCLUDE]
//                        Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                  //  hideProgressBar()
                    // [END_EXCLUDE]
                }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
//
    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            accountName.text = ""
        }
        signInButton.visibility = View.VISIBLE
        signOutButton.visibility = View.GONE

        val bundle= Bundle()
        bundle.putInt("pageNum",3)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("bundle",bundle)
        startActivity(intent)
        //startActivity( Intent(this, MainActivity::class.java))
    }
//
//    private fun revokeAccess() {
//        // Firebase sign out
//        auth.signOut()
//
//        // Google revoke access
//        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
//            updateUI(null)
//        }
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
//        hideProgressBar()
//        if (user != null) {
//            binding.status.text = getString(R.string.google_status_fmt, user.email)
//            binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)
//
//            binding.signInButton.visibility = View.GONE
//            binding.signOutAndDisconnect.visibility = View.VISIBLE
//        } else {
//            binding.status.setText(R.string.signed_out)
//            binding.detail.text = null
//
//            binding.signInButton.visibility = View.VISIBLE
//            binding.signOutAndDisconnect.visibility = View.GONE
//        }
//    }


}
