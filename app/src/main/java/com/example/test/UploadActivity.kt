package com.example.test

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class UploadActivity : AppCompatActivity() {

        var selected: Uri? = null

        var mAuth : FirebaseAuth? = null
        var mAuthList: FirebaseAuth.AuthStateListener? = null
        var firebaseDatabase : FirebaseDatabase? = null
        var myRef: DatabaseReference? = null
        var mStorageRef: StorageReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase!!.reference
        mStorageRef = FirebaseStorage.getInstance().reference


    }

    fun upload(view: View)
    {

        val uuid = UUID.randomUUID()
        val inm = "Images/$uuid.jpg"

        val stoarageref = mStorageRef!!.child(inm)
        stoarageref.putFile(selected!!).addOnSuccessListener { taskSnapshot ->

            val usr = mAuth!!.currentUser
            val usrem = usr!!.email.toString()
            val usrtpc = tvp.text.toString()
            val usrcon = etc.text.toString()


            val uuid1 = UUID.randomUUID()
            val str = uuid1.toString()
            // Database Storing of Meta Data -from here
            myRef!!.child("Articles").child(str).child("UserName").setValue(usr)
            myRef!!.child("Articles").child(str).child("UserEmail").setValue(usrem)
            myRef!!.child("Articles").child(str).child("Topic").setValue(usrtpc)
            myRef!!.child("Articles").child(str).child("Content").setValue(usrcon)
            stoarageref.downloadUrl.addOnSuccessListener { uri ->
                val url = uri.toString()
                myRef!!.child("Articles").child(str).child("URL-Info").setValue(url)
            }

        }
            .addOnCompleteListener { task ->
                if(task.isComplete)
                {
                    Toast.makeText(applicationContext,"Article Shared!!! :) ", Toast.LENGTH_LONG).show()
                    //Intent to take back the user back to Feed Activity!!!
                    val int = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(int)
                }
            }
            .addOnFailureListener { exception ->
                if(null != exception)
                {
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }


    }

    fun selectimage(view: View)
    {

        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
        else
        {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode ==2 && resultCode == Activity.RESULT_OK && data!= null)
        {
            selected = data.data
            try {
                @Suppress("DEPRECATION") val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selected)
                imageView.setImageBitmap(bitmap)
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }



}
