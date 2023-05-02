package com.example.projectandroid.Screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.projectandroid.MainActivity2
import com.example.projectandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign.*

class sign : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        auth = Firebase.auth
        db = Firebase.firestore

        phone.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 10) {
                textInputLayout3.error = "no error"
            } else if (text.length < 10) {
                textInputLayout3.error = null
            }
        }

        btn_sign.setOnClickListener {
            if (username.text!!.isEmpty() || email.text!!.isEmpty() || password.text!!.isEmpty() || phone.text!!.isEmpty()) {
                Toast.makeText(this, "The field is empty", Toast.LENGTH_SHORT).show()
            } else {
                createNewAccount(email.text.toString(), password.text.toString())
            }
        }
    }

    private fun createNewAccount(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Log.e("izz", "user ${user!!.uid} + ${user.email}")
                addUser(username.text.toString(), user.email!!, password.text.toString(), phone.text.toString())
                val i = Intent(this, login::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "فشل في التسجيل", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUser(name: String, email: String, pass: String, phone: String) {
        var user = hashMapOf("name" to name, "email" to email, "password" to pass, "phone" to phone)
        db!!.collection("users").add(user).addOnSuccessListener { documentReference ->
            Log.e("Document ID", documentReference.id)
        }.addOnFailureListener { exception ->
        }
    }
}


