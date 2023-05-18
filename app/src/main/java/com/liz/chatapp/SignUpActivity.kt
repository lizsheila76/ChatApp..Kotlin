package com.liz.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.liz.chatapp.databinding.ActivitySignUpBinding
import com.liz.chatapp.model.User
import com.liz.chatapp.utils.toast

class SignUpActivity : AppCompatActivity() {
    lateinit var Imge: ImageView
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var BtnSignUp: Button
    lateinit var auth: FirebaseAuth
    lateinit var DBRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Imge = findViewById(R.id.image)
        edtName = findViewById(R.id.name)
        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        BtnSignUp = findViewById(R.id.signup)
        auth = FirebaseAuth.getInstance()

        BtnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val name = edtName.text.toString()
            val password = edtPassword.text.toString()
            signUp(name,email,password)
        }
    }


    private fun signUp(name: String,email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    toast("Sign up successful")
                } else {
                    toast("An error occurred")
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        DBRef = FirebaseDatabase.getInstance().reference
        DBRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}