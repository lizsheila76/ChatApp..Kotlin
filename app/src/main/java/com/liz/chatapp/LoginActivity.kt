package com.liz.chatapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.liz.chatapp.databinding.ActivityLoginBinding
import com.liz.chatapp.utils.toast

class LoginActivity : AppCompatActivity() {
    lateinit var Image: ImageView
    lateinit var EdtEmail: EditText
    lateinit var EdtPassword: EditText
    lateinit var BtnLogin: Button
    lateinit var BtnSignUp: Button
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Image = findViewById(R.id.imageView)
        EdtEmail = findViewById(R.id.editEmail)
        EdtPassword = findViewById(R.id.edtPassword)
        BtnLogin = findViewById(R.id.btnLogin)
        BtnSignUp = findViewById(R.id.btnSignUp)
        auth = FirebaseAuth.getInstance()

        BtnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        BtnLogin.setOnClickListener {
            login()

        }


    }


    private fun login() {
        val email = EdtEmail.text.toString()
        val password = EdtPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"You have logged in successfully",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"User does not exist",Toast.LENGTH_LONG).show()
                }
            }
    }
}