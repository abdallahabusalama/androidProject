package com.example.projectandroid.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectandroid.R
import kotlinx.android.synthetic.main.activity_add.*

class add : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        addcategories.setOnClickListener {
            startActivity(Intent(this,add_categories::class.java))
        }
        addproducts.setOnClickListener {
            startActivity(Intent(this,add_products::class.java))
        }
    }
}