package com.example.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectandroid.RecyclerAdapter.MyModel1
import com.example.projectandroid.RecyclerAdapter.MyRecyclerAdapter3
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_details__category__user.*

class details_Category_User : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var cid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details__category__user)

        cid = intent.getStringExtra("idcat").toString()
        var db = FirebaseFirestore.getInstance()
        var ref = db.collection("categories").document(cid)
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null) {
                var data = documentSnapshot.data
                var name = data!!["name"] as String
                var image = data["image"] as String
                textView25.text = name
                Glide.with(this).load(image).into(imageView11)
                Log.e("izz", name)
            } else {
                Toast.makeText(this, "Failer", Toast.LENGTH_LONG).show()
            }
        }
            .addOnFailureListener { exception ->
            }

        getAllproduct()
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
                    recycler8.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    recycler8.setHasFixedSize(true)
                    recycler8.adapter = adapter
                }
            }
    }
}