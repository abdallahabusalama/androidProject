package com.example.projectandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectandroid.RecyclerAdapter.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.recycle4
import kotlinx.android.synthetic.main.fragment_search.view.*

class search : Fragment() {
    var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.btn_search.setOnClickListener {
            productData(editText.text.toString())
            productData2(editText.text.toString())
        }
        val catData = ArrayList<MyModel>()
        db!!.collection("categories").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val id = document.id
                            val data = document.data
                            val productName = data["name"] as String?
                            val productImage = data["image"] as String?
                            catData.add(MyModel(id, productImage, productName))
                        }
                        var adapter = MyRecyclerAdapter5(view.context, catData)
                        RecyclerView55.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        RecyclerView55.adapter = adapter
                    }
                }

        val productdata = ArrayList<MyModel1>()
        db!!.collection("products").get()
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
                        var adapter = MyRecyclerAdapter3(view.context, productdata)
                        recycle4.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recycle4.setHasFixedSize(true)
                        recycle4.adapter = adapter

                    }
                }
        return view
    }

    private fun productData(name: String) {
        val productdata = ArrayList<MyModel1>()
        db!!.collection("products").orderBy("name").startAt(name).get()//.whereEqualTo("name", name)
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
                        var adapter = view?.let { MyRecyclerAdapter3(it.context, productdata) }
                        recycle4.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recycle4.adapter = adapter
                    }
                }
    }

    private fun productData2(name: String) {
        val catData = ArrayList<MyModel>()
        db!!.collection("categories").orderBy("name").startAt(name).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val id = document.id
                            val data = document.data
                            val productName = data["name"] as String?
                            val productImage = data["image"] as String?
                            catData.add(MyModel(id, productImage, productName))
                        }
                    }
                    var adapter = view?.let { MyRecyclerAdapter5(it.context, catData) }
                    RecyclerView55.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    RecyclerView55.adapter = adapter
                }
    }
}