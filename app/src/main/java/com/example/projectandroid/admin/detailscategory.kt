package com.example.projectandroid.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectandroid.R
import com.example.projectandroid.RecyclerAdapter.MyModel
import com.example.projectandroid.RecyclerAdapter.MyModel1
import com.example.projectandroid.RecyclerAdapter.MyRecyclerAdapter1
import com.example.projectandroid.RecyclerAdapter.MyRecyclerAdapter3
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_detailscategory.*
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_product_details.*

class detailscategory : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var cid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailscategory)
        cid = intent.getStringExtra("idcat").toString()
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("categories").document(cid)
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null) {
                var data = documentSnapshot.data
                var name = data!!["name"] as String
                var image = data["image"] as String
                textView8.text = name
                Glide.with(this).load(image).into(imageView5)
                Log.e("izz", name)
            } else {
                Toast.makeText(this, "Failer", Toast.LENGTH_LONG).show()
            }
        }
                .addOnFailureListener { exception ->
                }
        cid = intent.getStringExtra("idcat").toString()
        button5.setOnClickListener {
            val intent = Intent(this, editcategories::class.java)
            intent.putExtra("prodId", cid)
            startActivity(intent)
        }

        getAllproduct()
        button4.setOnClickListener {
            db.collection("categories").document(cid).delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Deleted Success", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Main::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Deleted Not Success", Toast.LENGTH_LONG).show()
                    }
        }
    }

    private fun getAllproduct() {
        var namecat = intent.getStringExtra("namecat")!!
        val productdata = ArrayList<MyModel1>()
        db.collection("products").whereEqualTo("rate", namecat).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val id = document.id
                            val data = document.data
                            val name = data["name"] as String?
                            val price = data["price"] as String?
                            val Image = data["image"] as String?
                            val description = data["description"] as String?
                            val rate = data["rate"] as String?
                            productdata.add(MyModel1(id, name, price, description, rate, Image))
                        }
                        var adapter = MyRecyclerAdapter3(this, productdata)
                        recycler3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recycler3.setHasFixedSize(true)
                        recycler3.adapter = adapter
                    }
                }
    }
}