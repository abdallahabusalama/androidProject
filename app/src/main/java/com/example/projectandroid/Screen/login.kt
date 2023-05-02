package com.example.projectandroid.Screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectandroid.MainActivity2
import com.example.projectandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        db = Firebase.firestore
        btn_login.setOnClickListener {
            if (email2.text!!.isEmpty() || password2.text!!.isEmpty()) {
                Toast.makeText(this, "The field is empty", Toast.LENGTH_SHORT).show()
            } else {
                createNewAccount(email2.text.toString(), password2.text.toString())
            }
        }
    }

    private fun createNewAccount(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity2::class.java))
                val user = auth.currentUser
                Log.e("izz", "user ${user!!.uid} + ${user.email}")
            } else {
                Toast.makeText(this, "فشل في التسجيل", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
