package com.example.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val buttonnav = findViewById<BottomNavigationView>(R.id.bottomnav)
        val n = findNavController(R.id.fragment)
        val appnav =
            AppBarConfiguration(setOf(R.id.home, R.id.search, R.id.portfolio))
      //  setupActionBarWithNavController(n, appnav)
        buttonnav.setupWithNavController(n)
    }
}

















