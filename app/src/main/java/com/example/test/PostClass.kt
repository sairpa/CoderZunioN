package com.example.test

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*

class PostClass(private val useremail:ArrayList<String>,
                private val userimage:ArrayList<String>,private val usertopic:ArrayList<String>,
                private val usercontent:ArrayList<String>, private val context: Activity
): ArrayAdapter<String>(context,R.layout.custom_view,useremail) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = context.layoutInflater
        val cuview = layout.inflate(R.layout.custom_view,null,true)

        cuview.usrnmecv.text = useremail[position]
        cuview.usrtpccv.text = usertopic[position]
        Picasso.get().load(userimage[position]).into(cuview.usricv)
        return cuview
    }
}