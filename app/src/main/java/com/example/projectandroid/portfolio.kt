package com.example.projectandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_portfolio.*
import kotlinx.android.synthetic.main.fragment_portfolio.view.*


class portfolio : Fragment() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = Firebase.auth
        db = Firebase.firestore
        val view = inflater.inflate(R.layout.fragment_portfolio, container, false)
        getprofil()
        view.edit.setOnClickListener {
            updategetprofil(tvtxtname.text.toString(), tvtxtemail.text.toString(), tvtxtpassword.text.toString(), tvtxtphone.text.toString())
        }
        view.button10.setOnClickListener {
            startActivity(Intent(context,product_cart::class.java))
        }
        return view
    }

    fun getprofil() {
        db!!.collection("users").get()
            .addOnSuccessListener { querySnapshot ->
                tvtxtname.setText(querySnapshot.documents.get(0).get("name").toString())
                tvtxtemail.setText(querySnapshot.documents.get(0).get("email").toString())
                tvtxtpassword.setText(querySnapshot.documents.get(0).get("password").toString())
                tvtxtphone.setText(querySnapshot.documents.get(0).get("phone").toString())
            }.addOnFailureListener { exception ->
            }
    }

    private fun updategetprofil(name: String, email: String, pass: String, phone: String) {
        val user = HashMap<String, Any>()
        user["name"] = name
        user["email"] = email
        user["password"] = pass
        user["phone"] = phone
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get().addOnSuccessListener { querySnapshot ->
            db!!.collection("users").document(querySnapshot.documents.get(0).id).update(user)
            Toast.makeText(context, "Eidt Portfolio Success", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
        }
    }
}