package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_product__details__user.*

class Product_Details_User : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    var id = ""
    var latProd = ""
    var lngProd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product__details__user)
        val x = AnimationUtils.loadAnimation(this, R.anim.item)
        val y = AnimationUtils.loadAnimation(this, R.anim.item_img)
        val txt1 = AnimationUtils.loadAnimation(this, R.anim.item_txt)
        val txt2 = AnimationUtils.loadAnimation(this, R.anim.item_txt2)
        val txt3 = AnimationUtils.loadAnimation(this, R.anim.item_txt3)
        val btn = AnimationUtils.loadAnimation(this, R.anim.btn)

        val title = findViewById(R.id.textView19) as TextView
        val subtitle = findViewById(R.id.textView20) as TextView
        val imageView7 = findViewById(R.id.imageView8) as ImageView
        val textView16 = findViewById(R.id.textView21) as TextView
        val textView17 = findViewById(R.id.textView22) as TextView
        val textView18 = findViewById(R.id.textView23) as TextView
        val button4 = findViewById(R.id.button7) as Button
        val button8 = findViewById(R.id.button8) as Button
        val textView24 = findViewById(R.id.textView24) as Button
        val button9 = findViewById(R.id.button9) as Button

        title.startAnimation(y)
        subtitle.startAnimation(y)
        imageView7.startAnimation(y)
        button4.startAnimation(y)
        button8.startAnimation(y)
        textView24.startAnimation(y)
        button9.startAnimation(y)
        textView16.startAnimation(y)
        textView18.startAnimation(y)
        textView17.startAnimation(y)

        db = Firebase.firestore
        var z = 1;
        button8.setOnClickListener {
            z++
            textView23.text = z.toString()
        }
        button7.setOnClickListener {
            if (textView23.text.toString().toInt() > 1) {
                z--
            }
            textView23.text = z.toString()
        }

        id = intent.getStringExtra("productId")!!
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
                latProd = data!!["locationone"] as String
                lngProd = data!!["locationtow"] as String
                Log.e("izz", latProd)
                Log.e("izz", lngProd)
                textView19.text = name
                textView20.text = price
                textView21.text = desc
                textView22.text = rate
                Glide.with(this).load(image).into(imageView8)
            } else {
                Toast.makeText(this, "Failer", Toast.LENGTH_LONG).show()
            }
        }
                .addOnFailureListener { exception ->
                }

        textView24.setOnClickListener {
            var i = Intent(this, MapsActivity::class.java)
//            intent.putExtra("productId", id)
//            Log.e("izz", id)
//            intent.putExtra("latProd", latProd)
//            intent.putExtra("lngProd", lngProd)
            startActivity(i)
        }

        imageView12.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        button9.setOnClickListener {
            updateImage()
        }
    }

    private fun updateImage() {
        val user = HashMap<String, Any>()
        var cart = true
        user["cart"] = cart
        db.collection("products").document(id).update(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Add Product Cart Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                }
    }
}