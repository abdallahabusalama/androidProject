package com.example.projectandroid.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.projectandroid.R
import com.example.projectandroid.RecyclerAdapter.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_product_cart.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.rec2.*
import kotlinx.android.synthetic.main.rec2.view.*
import kotlin.math.abs

class Main : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    private lateinit var viewPager2: ViewPager2
    private val SliderHander = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        db = Firebase.firestore
        categoryData()
        getAllproduct()
        get()
        getAllproductaa()
        button11.setOnClickListener {
            startActivity(Intent(this,add::class.java))
        }
        viewPager2 = findViewById(R.id.viewpager)
        var slideritems: MutableList<SliderItem> = ArrayList()
        slideritems.add(SliderItem(R.drawable.aelan))
        slideritems.add(SliderItem(R.drawable.aelan2))
        slideritems.add(SliderItem(R.drawable.aelan3))
        slideritems.add(SliderItem(R.drawable.aelan4))
        slideritems.add(SliderItem(R.drawable.aelan6))
        slideritems.add(SliderItem(R.drawable.redmi))
        slideritems.add(SliderItem(R.drawable.aelan5))
        slideritems.add(SliderItem(R.drawable.aelan))
        slideritems.add(SliderItem(R.drawable.aelan2))
        slideritems.add(SliderItem(R.drawable.aelan3))
        slideritems.add(SliderItem(R.drawable.aelan4))
        slideritems.add(SliderItem(R.drawable.aelan6))
        slideritems.add(SliderItem(R.drawable.redmi))
        slideritems.add(SliderItem(R.drawable.aelan5))
        viewPager2.adapter = SliderAdpter(slideritems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val x = CompositePageTransformer()
        x.addTransformer(MarginPageTransformer(30))
        x.addTransformer { page, position ->
            var r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        viewPager2.setPageTransformer(x)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                SliderHander.removeCallbacks(Slidere)
                SliderHander.postDelayed(Slidere, 3000)
            }
        })
    }

    private val Slidere = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun categoryData() {
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
                        var adapter = MyRecyclerAdapter2(this, catData)
                        recycle2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        recycle2.setHasFixedSize(true)
                        recycle2.adapter = adapter
                    }
                }
    }

    private fun getAllproduct() {
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
                        var adapter = MyRecyclerAdapter1(this, productdata)
                        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        recycle.setHasFixedSize(true)
                        recycle.adapter = adapter
                    }
                }
    }

    private fun get() {
        val productdata = ArrayList<MyModel1>()
        db!!.collection("products").whereGreaterThanOrEqualTo("rate2", 5).limit(7).orderBy("rate2", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            document.contains("rate")
                            val id = document.id
                            val data = document.data
                            val name = data["name"] as String?
                            val price = data["price"] as String?
                            val Image = data["image"] as String?
                            val description = data["description"] as String?
                            val rate = data["rate"] as String?
                            productdata.add(MyModel1(id, name, price, description, rate, Image))
                        }
                        var adapter = MyRecyclerAdapter1(this, productdata)
                        ab.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        ab.adapter = adapter
                    }
                }
    }

    private fun getAllproductaa() {
        var asd = true
        val productdata = ArrayList<MyModel1>()
        db!!.collection("products").whereEqualTo("cart", asd).get()
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
                        var adapter = MyRecyclerAdapter1(this, productdata)
                        recyclerView4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView4.setHasFixedSize(true)
                        recyclerView4.adapter = adapter
                    }
                }
    }
}