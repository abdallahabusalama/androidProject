package com.example.projectandroid.Screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.projectandroid.R
import kotlinx.android.synthetic.main.fragment_fourth.view.*

class fourthFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_fourth, container, false)
//        val fragmentManager = activity?.supportFragmentManager
//        val fragmentTransaction = fragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(R.id.fourthFragment2,fragment)
//        fragmentTransaction?.commit()
        val viewpager = activity?.findViewById<ViewPager2>(R.id.veiw_pager)

        view.button.setOnClickListener {
            findNavController().navigate(R.id.button6)
            viewpager?.currentItem = 2
        }
        view.button2.setOnClickListener {
            findNavController().navigate(R.id.btn_sign)
            viewpager?.currentItem = 1
        }

        return view
    }
}