package com.example.projectandroid.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectandroid.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_admin.*

class login_admin : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)
        auth = Firebase.auth
        db = Firebase.firestore
        button6.setOnClickListener {
            if (emailadmin.text!!.isEmpty() || passwordadmin.text!!.isEmpty()) {
                Toast.makeText(this, "The field is empty", Toast.LENGTH_SHORT).show()
            } else {
                createNewAccount(emailadmin.text.toString(), passwordadmin.text.toString())
            }
        }
    }

    private fun createNewAccount(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Main::class.java))
                val user = auth.currentUser
                Log.e("izz", "user ${user!!.uid} + ${user.email}")
            } else {
                Toast.makeText(this, "فشل في التسجيل", Toast.LENGTH_SHORT).show()
            }
        }
    }
}