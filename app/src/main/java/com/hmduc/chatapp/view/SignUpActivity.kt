package com.hmduc.chatapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hmduc.chatapp.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dataRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val userName = edtname.text.toString()
            val email = edtMail.text.toString()
            val pass = edtPass.text.toString()
            val conformPass = edtConfirmPass.text.toString()

            if (userName.isEmpty()) {
                Toast.makeText(applicationContext, "UserName is required", Toast.LENGTH_SHORT).show()
            }

            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Email is required", Toast.LENGTH_SHORT).show()
            }

            if (pass.isEmpty()) {
                Toast.makeText(applicationContext, "Password is required", Toast.LENGTH_SHORT).show()
            }

            if (conformPass.isEmpty()) {
                Toast.makeText(applicationContext, "Confirm Password is required", Toast.LENGTH_SHORT).show()
            }

            if (!pass.equals(conformPass)) {
                Toast.makeText(applicationContext, "Password not match", Toast.LENGTH_SHORT).show()
            }

            registerUser(email, pass, userName)
        }

        btnLogIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun registerUser(email: String, pass: String, userName: String) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {
                    if (it.isComplete) {
                        val user: FirebaseUser? = auth.currentUser
                        val userId: String = user!!.uid
                        dataRef = FirebaseDatabase.getInstance().getReference("User").child(userId)

                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap["userId"] = userId
                        hashMap["userName"] = userName
                        hashMap["profileImage"] = ""

                        dataRef.setValue(hashMap).addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                edtMail.setText("")
                                edtname.setText("")
                                edtPass.setText("")
                                edtConfirmPass.setText("")
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }
                        }
                    }

                }
    }
}