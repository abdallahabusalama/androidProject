package com.example.projectandroid.Screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.projectandroid.R
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_second.view.*

class secondFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_second, container, false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.veiw_pager)
        v.btn1.setOnClickListener {
            findNavController().navigate(R.id.threadFragment2)
            viewpager?.currentItem = 2
        }
        return v
    }

}