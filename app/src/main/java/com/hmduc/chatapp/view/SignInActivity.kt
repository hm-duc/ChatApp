package com.hmduc.chatapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hmduc.chatapp.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.btnLogIn
import kotlinx.android.synthetic.main.activity_sign_in.btnSignUp
import kotlinx.android.synthetic.main.activity_sign_in.edtPass

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        btnLogIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this,"Email and Password are required!",Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        edtEmail.setText("")
                        edtPass.setText("")
                        startActivity(Intent(this, UserActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this,"Email or Password invalid!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}