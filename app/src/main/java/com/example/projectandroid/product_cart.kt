package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectandroid.RecyclerAdapter.MyModel1
import com.example.projectandroid.RecyclerAdapter.MyRecyclerAdapter3
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_product_cart.*

class product_cart : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cart)
        imageView13.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
        getAllproduct()
        getprofil()
    }

    fun getprofil() {
        db!!.collection("users").get()
                .addOnSuccessListener { querySnapshot ->
                    textView18.setText(querySnapshot.documents.get(0).get("name").toString())
                    textView27.setText(querySnapshot.documents.get(0).get("email").toString())
                }.addOnFailureListener { exception ->
                }
    }

    private fun getAllproduct() {
        var asd = true
        val productdata = ArrayList<MyModel1>()
        db.collection("products").whereEqualTo("cart", asd).get()
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
                        recycle9.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recycle9.setHasFixedSize(true)
                        recycle9.adapter = adapter
                    }
                }
    }
}