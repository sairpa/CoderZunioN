package com.example.test

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        data()
    }

    fun data()
    {
        var int = intent
        val email = int.getStringExtra("Email")
        val topic  = int.getStringExtra("Topic")
        val content = int.getStringExtra("Content")
        val Image = int.getStringExtra("URL")
        tvtp.setText(topic.toString())
        tvem.setText(email.toString())
        tvcnt.setText(content.toString())
        Picasso.get().load(Image.toString()).into(ivimg)
        val tv:TextView = findViewById(R.id.tvcnt)
        tv.setMovementMethod(ScrollingMovementMethod())
    }

}
