package com.example.projectandroid

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Double.parseDouble


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var mLocation: Location? = null
    var lat = 0.0
    var lng = 0.0
    var pId =""
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                mLocation = location
            }
            lat = mLocation!!.latitude
            lng = mLocation!!.longitude
            val gaza = LatLng(lat, lng)
//        pId = intent!!.getStringExtra("productId")!!
//        var db = FirebaseFirestore.getInstance()
//        var ref = db.collection("products").document(pId)
//        ref.get().addOnSuccessListener { documentSnapshot ->
//            var data = documentSnapshot.data
//            var latProd = data!!["locationone"] as String
//            val lngProd = data!!["locationtow"] as String
            //      val ccc = LatLng(parseDouble(latProd).toDouble(), parseDouble(lngProd).toDouble())
            val ccc = LatLng(-8.43, 20.89)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gaza, 16f))
            mMap.addMarker(MarkerOptions().position(gaza).title("Marker"))
            mMap.addMarker(MarkerOptions().position(ccc).title("Marker"))
            mMap.addPolyline(PolylineOptions().add(gaza, ccc))

            if (mFusedLocationClient != null) {
                mFusedLocationClient!!.removeLocationUpdates(this)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
      //  db = Firebase.firestore
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
                getLocationRequest(),
                mLocationCallback,
                Looper.myLooper()
        )
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true
//        lat = mLocation!!.latitude
//        lng = mLocation!!.longitude
//        val gaza = LatLng(lat, lng)
//        pId = intent.getStringExtra("productId")!!
//        var db = FirebaseFirestore.getInstance()
//        var ref = db.collection("products").document(pId)
//        ref.get().addOnSuccessListener { documentSnapshot ->
//            var data = documentSnapshot.data
//            var latProd = data!!["locationone"] as String
//            val lngProd = data!!["locationtow"] as String
//           val ccc = LatLng(parseDouble(latProd).toDouble(), parseDouble(lngProd).toDouble())
//        val ccc = LatLng(-34.0, 151.0)
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gaza, 16f))
//        mMap.addMarker(MarkerOptions().position(gaza).title("Marker"))
//        mMap.addMarker(MarkerOptions().position(ccc).title("Marker"))
//        mMap.addPolyline(PolylineOptions().add(gaza, ccc))

     /*   mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            Toast.makeText(
                    this,
                    latLng.latitude.toString() + " " + latLng.longitude.toString(),
                    Toast.LENGTH_SHORT
            ).show()
        }*/
    }
   // }
    fun getLocationRequest(): LocationRequest? {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        return locationRequest
    }

    fun geta() {
//        lat = mLocation!!.latitude
//        lng = mLocation!!.longitude
//        val gaza = LatLng(lat, lng)
//        pId = intent!!.getStringExtra("latProd")!!
//        pid = intent!!.getStringExtra("lngProd")!!

//        pId = intent!!.getStringExtra("prodId")!!
        //   Log.e("izz", pId)
//        var db = FirebaseFirestore.getInstance()
//        var ref = db.collection("products").document(pId)
//        ref.get().addOnSuccessListener { documentSnapshot ->
//            var data = documentSnapshot.data
//            var latProd = data!!["locationone"] as String
//            val lngProd = data["locationtow"] as String
        //   val ccc = LatLng(parseDouble(pId).toDouble(), parseDouble(pid).toDouble())
//        val ccc = LatLng(-34.0, 151.0)
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gaza, 16f))
//        mMap.addMarker(MarkerOptions().position(gaza).title("Marker"))
//        mMap.addMarker(MarkerOptions().position(ccc).title("Marker"))
//        mMap.addPolyline(PolylineOptions().add(gaza, ccc))
    }
}
