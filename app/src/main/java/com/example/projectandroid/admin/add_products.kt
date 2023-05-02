package com.example.projectandroid.admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
import kotlinx.android.synthetic.main.activity_add_products.*
import java.util.*

class add_products : AppCompatActivity(), IPickResult, OnMapReadyCallback {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    var markerB = true
    private lateinit var mMap: GoogleMap
    var lat = ""
    var lng = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)
        addprod.setOnClickListener {
            add_product(
                    nameproduct.text.toString(),
                    priceproduct.text.toString(),
                    descproduct.text.toString(),
                    productrate.selectedItem.toString(),
                    productrate2.selectedItem.toString().toInt(),
                    path
            )
        }
        image_product.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

        btn_save.setOnClickListener {
            startActivity(Intent(this, Main::class.java))
        }
    }

    override fun onPickResult(r: PickResult?) {
        image_product.setImageBitmap(r!!.bitmap)
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

    private fun add_product(name: String, price: String, description: String, rate: String, rate2: Int, image: String?) {
        var cart = false
        var product = hashMapOf("name" to name, "price" to price, "description" to description, "rate" to rate, "rate2" to rate2, "image" to image, "cart" to cart, "locationone" to lat, "locationtow" to lng)
        db.collection("products").add(product).addOnSuccessListener { documentReference ->
            Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show()
            Log.e("izz", documentReference.id)
        }.addOnFailureListener { exception ->
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0!!
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.setOnMapClickListener { point ->
            lat = point.latitude.toString()
            lng = point.longitude.toString()
            if (markerB) {
                mMap.clear()
                markerB = false
            }
            if (markerB == false) {
                mMap.addMarker(MarkerOptions().position(LatLng(lat.toDouble(), lng.toDouble())).title("Marker in city"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), lng.toDouble()), 16f))
                markerB = true
            }
        }
    }
}