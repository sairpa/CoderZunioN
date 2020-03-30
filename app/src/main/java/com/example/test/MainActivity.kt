package com.example.test

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    var sp:SharedPreferences? =null
    var ed:SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("login",0)
        ed = sp!!.edit()
        if(sp!!.getBoolean("login",false))
        {
            val int = Intent(applicationContext,SignUp::class.java)
            startActivity(int)
        }
    }
    fun click(view:View)
    {
        val int = Intent(applicationContext,SignUp::class.java)
        startActivity(int)
        ed!!.putBoolean("login",true).apply()
    }

}
