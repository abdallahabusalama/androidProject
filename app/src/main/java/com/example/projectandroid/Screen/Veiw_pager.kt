package com.example.projectandroid.Screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectandroid.R
import kotlinx.android.synthetic.main.fragment_veiw_pager.view.*

class Veiw_pager : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_veiw_pager, container, false)
        val fragment = arrayListOf<Fragment>(
            FirstFragment(),
            secondFragment(),
            threadFragment(),
            fourthFragment()
        )
        val adapter = Viewpager(fragment, requireActivity().supportFragmentManager, lifecycle)
        view.fragmentviewpager.adapter = adapter
        return view
    }

}