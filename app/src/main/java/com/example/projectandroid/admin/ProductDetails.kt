package com.example.projectandroid.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.projectandroid.R
import com.example.projectandroid.RecyclerAdapter.MyModel1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.rec2.*

class ProductDetails : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        db = Firebase.firestore
        var id = intent.getStringExtra("productId")!!
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("products").document(id)
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null) {
                var data = documentSnapshot.data
                var name = data!!["name"] as String
                val price = data["price"] as String
                val desc = data["description"] as String
                val rate = data["rate"] as String
                var image = data["image"] as String
                detalis_name.text = name
                detalis_price.text = price
                detalis_decs.text = desc
                detalis_rate.text = rate
                Glide.with(this).load(image).into(detalis_image)
                Log.e("izz", name)
            } else {
                Toast.makeText(this, "Failer", Toast.LENGTH_LONG).show()
            }
        }
                .addOnFailureListener { exception ->
                }
        detalis_edit.setOnClickListener {
            val intent = Intent(this, edit::class.java)
            intent.putExtra("prodId", id)
            startActivity(intent)
        }

        detalis_delete.setOnClickListener {
            db.collection("products").document(id).delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Deleted Success", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Main::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Deleted Not Success", Toast.LENGTH_LONG).show()
                    }
        }
    }
}