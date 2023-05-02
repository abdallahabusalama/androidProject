package com.example.projectandroid

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.projectandroid.RecyclerAdapter.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlin.math.abs

class home : Fragment() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var viewPager2: ViewPager2
    private val SliderHander = Handler()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        val catData = ArrayList<MyModel>()
        db!!.collection("categories").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val id = document.id
                            val data = document.data
                            val productName = data["name"] as String?
                            val productImage = data["image"] as String?
                            catData.add(MyModel(id, productImage, productName)) }
                        var adapter = MyRecyclerAdapter5(view.context, catData)
                        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView.adapter = adapter

                    }
                }
        // show all product
        val productdata = ArrayList<MyModel1>()
        db!!.collection("products").get()
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
                            productdata.add(MyModel1(id,name, price, description, rate,Image))
                        }
                        var adapter = MyRecyclerAdapter4(view.context, productdata)
                        recyclerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView2.adapter = adapter

                    }
                }
        // show all product food
        var namecat = "Food"
        val productdatafood = ArrayList<MyModel1>()
        db!!.collection("products").whereEqualTo("rate", namecat).get()
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
                            productdatafood.add(MyModel1(id,name, price, description, rate, Image))
                        }
                        var adapter = MyRecyclerAdapter4(view.context, productdatafood)
                        recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView3.adapter = adapter
                    }
                }
        // show all product more
        var namecate = "More"
        val productdatamore = ArrayList<MyModel1>()
        db!!.collection("products").whereEqualTo("rate", namecate).get()
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
                            productdatamore.add(MyModel1(id,name, price, description, rate, Image))
                        }
                        var adapter = MyRecyclerAdapter4(view.context, productdatamore)
                        recyclerView5.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView5.adapter = adapter
                    }
                }
        // show all product Health
        var namecatea = "Health"
        val productdatahealth = ArrayList<MyModel1>()
        db!!.collection("products").whereEqualTo("rate", namecatea).get()
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
                            productdatahealth.add(MyModel1(id,name, price, description, rate,Image))
                        }
                        var adapter = MyRecyclerAdapter4(view.context, productdatahealth)
                        recyclerView6.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView6.adapter = adapter
                    }
                }

        viewPager2 = view.findViewById(R.id.viewpager)
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
        return view
    }

    private val Slidere = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

}