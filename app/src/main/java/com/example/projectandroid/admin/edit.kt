package com.example.projectandroid.admin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.projectandroid.R
import com.example.projectandroid.RecyclerAdapter.MyModel1
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_add_products.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.fragment_portfolio.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class edit : AppCompatActivity(), IPickResult {

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    var pId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_edit)
        db = Firebase.firestore
         auth = Firebase.auth
         storage = Firebase.storage
         reference = storage!!.reference
         progressDialog = ProgressDialog(this)
         progressDialog.setMessage("جاري التحميل")
         progressDialog.setCancelable(false)

        pId = intent!!.getStringExtra("prodId")!!
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("products").document(pId)
        ref.get().addOnSuccessListener { documentSnapshot ->
            var data = documentSnapshot.data
            var name = data!!["name"] as String
            val price = data["price"] as String
            val desc = data["description"] as String
            val rate = data["rate"] as String
            var image = data["image"] as String
            editname.setText(name)
            editprice.setText(price)
            editdescription.setText(desc)
            //      editrate.selectedItem(rate)
            //      spinnerCategoriesName(rate)
            Glide.with(this).load(image).into(editimage)
        }.addOnFailureListener { exception ->
        }

        updata.setOnClickListener {
            edit_product(editname.text.toString(), editprice.text.toString(), editdescription.text.toString(), editrate.selectedItem.toString(),path!!)
            startActivity(Intent(this, ProductDetails::class.java))
        }
        editimage.setOnClickListener {
             PickImageDialog.build(PickSetup()).show(this)
         }
     }

     override fun onPickResult(r: PickResult?) {
         editimage.setImageBitmap(r!!.bitmap)
         uploadImage(r.uri)
     }

     private fun uploadImage(uri: Uri?) {
         progressDialog.show()
         reference!!.child("products/" + UUID.randomUUID().toString()).putFile(uri!!)
                 .addOnSuccessListener { taskSnapshot ->
                     progressDialog.dismiss()
                     taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                         path = uri.toString()
                     }
                     Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                 }.addOnFailureListener { exception ->
                 }
     }

     private fun add_product(name: String, price: String, description: String, rate: String,rate2:Int, image: String) {
         var product = hashMapOf("name" to name, "price" to price, "description" to description, "rate" to rate,"rate2" to rate2, "image" to image)
         db.collection("products").add(product).addOnSuccessListener { documentReference ->
             Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show()
             Log.e("Documenid", documentReference.id)
         }.addOnFailureListener { exception ->
         }
     }

    private fun edit_product(name: String, price: String, description: String, rate: String,image: String) {
        var product = HashMap<String, Any>()
        product["name"] = name
        product["price"] = price
        product["description"] = description
        product["rate"] = rate
        product["image"] = image
        //  hashMapOf("name" to name, "price" to price, "description" to description, "rate" to rate, "location" to location, "image" to image)
        db.collection("products").document(pId).update(product)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Edit Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                }
    }
 }