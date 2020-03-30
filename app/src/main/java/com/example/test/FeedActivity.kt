package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    var useremailfrommFB:ArrayList<String> = ArrayList<String>()
    var usertopicfrommFB:ArrayList<String> = ArrayList<String>()
    var usercontentfrommFB:ArrayList<String> = ArrayList<String>()
    var userImagefrommFB:ArrayList<String> = ArrayList<String>()
    var firedatabse: FirebaseDatabase? = null
    var myRef : DatabaseReference? = null
    var adapter: PostClass? = null


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_article,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.Add_article)
        {
            val int = Intent(applicationContext,UploadActivity::class.java)
            startActivity(int)
        }

        if (item.itemId == R.id.Logout)
        {
            Toast.makeText(applicationContext,"Feature in works!! Will be soon there :)", Toast.LENGTH_LONG).show()

        }

        if (item.itemId == R.id.Settings)
        {
            Toast.makeText(applicationContext,"Feature in works!! Will be soon there :)", Toast.LENGTH_LONG).show()

        }

        return super.onOptionsItemSelected(item)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        firedatabse = FirebaseDatabase.getInstance()
        myRef = firedatabse!!.getReference()
        adapter = PostClass(useremailfrommFB,userImagefrommFB,usertopicfrommFB,usercontentfrommFB,this)
        listview.adapter = adapter
        getDatafromFB()
        listview.setOnItemClickListener { _, _, position,_  ->
            var int = Intent(applicationContext,DetailActivity::class.java)
            int.putExtra("Email",useremailfrommFB[position])
            int.putExtra("Topic",usertopicfrommFB[position])
            int.putExtra("Content",usercontentfrommFB[position])
            int.putExtra("URL",userImagefrommFB[position])
            startActivity(int)
        }

        val inc : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        inc.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){

                R.id.feed -> {
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.explore ->
                {
                    val int = Intent(applicationContext,ExploreActivity::class.java)
                    startActivity(int)
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.discover -> {
                    val int = Intent(applicationContext,DiscoverActivity::class.java)
                    startActivity(int)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    val int = Intent(applicationContext,ProfileActivity::class.java)
                    startActivity(int)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }


    }

    fun getDatafromFB()
    {
        val newref = firedatabse!!.getReference("Articles")
        newref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {



            }

            override fun onDataChange(p0: DataSnapshot) {

                adapter!!.clear()
                userImagefrommFB.clear()
                usertopicfrommFB.clear()
                usercontentfrommFB.clear()
                useremailfrommFB.clear()

                for(snapshot in p0.children)
                {
                    val hashmap = snapshot.value as HashMap<String,String>

                    if(hashmap.size>0)
                    {
                        val email = hashmap["UserEmail"]
                        val content = hashmap["Content"]
                        val topic = hashmap["Topic"]
                        val Url = hashmap["URL-Info"]


                        if(email!= null && content!= null && topic!= null && Url!= null)
                        {
                            useremailfrommFB.add(email)
                            usercontentfrommFB.add(content)
                            usertopicfrommFB.add(topic)
                            userImagefrommFB.add(Url)
                        }
                        adapter!!.notifyDataSetChanged()

                    }
                }


            }

        })
    }




}
