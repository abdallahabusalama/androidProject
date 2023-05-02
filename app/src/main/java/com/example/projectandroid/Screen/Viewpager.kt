package com.example.projectandroid.Screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class Viewpager(list: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {


    private val fragmenrlist = list
    override fun getItemCount(): Int {
        return fragmenrlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmenrlist[position]
    }
}