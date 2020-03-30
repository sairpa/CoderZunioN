package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    var mAuth: FirebaseAuth? =null
    var mAuthList: FirebaseAuth.AuthStateListener? = null
    private var Private = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        mAuthList = FirebaseAuth.AuthStateListener {  }

    }


    fun signIn(view: View)
    {

        mAuth!!.signInWithEmailAndPassword(etusrnm.text.toString(),etpswd.text.toString())
            .addOnCompleteListener { task ->

                //Intent here
                var int = Intent(applicationContext,FeedActivity::class.java)
                startActivity(int)


            }.addOnFailureListener { exception ->
                if(exception!= null)
                {
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

    }

    fun signUp(view: View)
    {

        mAuth!!.createUserWithEmailAndPassword(etusrnm.text.toString(),etpswd.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(applicationContext,"User Created! :) ", Toast.LENGTH_LONG).show()
                    //Intent
                    var int = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(int)
                }
            }.addOnFailureListener { exception ->
                if(exception!= null)
                {
                    Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }




}
