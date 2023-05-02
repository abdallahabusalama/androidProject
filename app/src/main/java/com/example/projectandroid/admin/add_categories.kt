package com.example.projectandroid.admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_add_categories.*
import kotlinx.android.synthetic.main.activity_add_products.*
import kotlinx.android.synthetic.main.adapter.view.*
import java.util.*

class add_categories : AppCompatActivity(), IPickResult {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_categories)
        auth = Firebase.auth
        db = Firebase.firestore
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)
        showcat.setOnClickListener {
            startActivity(Intent(this, Main::class.java))
        }
        addcat.setOnClickListener {
           add_categories(namecategories.selectedItem.toString(),path)
        }
        button3.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }
    }
    override fun onPickResult(r: PickResult?) {
    button3.setImageBitmap(r!!.bitmap)
        uploadImage(r.uri)
    }

    private fun uploadImage(uri: Uri?) {
        progressDialog.show()
        reference!!.child("categories/" + UUID.randomUUID().toString()).putFile(uri!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        path = uri.toString()
                    }
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                }
    }
    private fun add_categories(name:String, image: String?) {
        var categorie = hashMapOf("name" to name , "image" to image)
        db.collection("categories").add(categorie).addOnSuccessListener { documentReference ->
            Toast.makeText(this,"Add Success",Toast.LENGTH_SHORT).show()
            Log.e("izz", documentReference.id)
        }.addOnFailureListener { exception ->
        }
    }
}
