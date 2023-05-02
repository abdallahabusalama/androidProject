package com.example.projectandroid.admin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.editname
import kotlinx.android.synthetic.main.activity_editcategories.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class editcategories : AppCompatActivity(), IPickResult {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    var pId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcategories)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)

        pId = intent!!.getStringExtra("prodId")!!
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("categories").document(pId)
        ref.get().addOnSuccessListener { documentSnapshot ->
            var data = documentSnapshot.data
            var name = data!!["name"] as String
            var image = data["image"] as String
            editTextTextPersonName.setText(name)
            Glide.with(this).load(image).into(imageView6)
        }.addOnFailureListener { exception ->
        }

        button62.setOnClickListener {
            edit_product(editTextTextPersonName.text.toString(),path!!)
            startActivity(Intent(this, ProductDetails::class.java))
        }
//
        imageView6.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

    }

    override fun onPickResult(r: PickResult?) {
        imageView6.setImageBitmap(r!!.bitmap)
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

    private fun edit_product(name: String,image: String) {
        var categories = HashMap<String, Any>()
        categories["name"] = name
        categories["image"] = image
       db.collection("categories").document(pId).update(categories)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Edit Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                }
    }
}









        /*
        db = Firebase.firestore
        auth = Firebase.auth
        firebaseStore = FirebaseStorage.getInstance()
        storageRefernce = FirebaseStorage.getInstance().reference

        imageView6.setOnClickListener {
            launchGallery()
        }

        button6.setOnClickListener {
            startActivity(Intent(this, ProductDetails::class.java))
        }

        pId = intent!!.getStringExtra("prodId")!!
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("categories").document(pId)
        ref.get().addOnSuccessListener { documentSnapshot ->
            var data = documentSnapshot.data
            var name = data!!["name"] as String
            var image = data["image"] as String
            editTextTextPersonName.setText(name)
            Glide.with(this).load(image).into(imageView6)
        }.addOnFailureListener { exception ->
        }
    }}
/*
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "categories/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                editimage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}*/