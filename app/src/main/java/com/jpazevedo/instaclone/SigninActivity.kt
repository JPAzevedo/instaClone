package com.jpazevedo.instaclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signup_link_btn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        login_btn.setOnClickListener{
            loginUser()
        }

    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val password = email_password.text.toString()

        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required.",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password is required.",Toast.LENGTH_LONG).show()
            else -> {

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this@SigninActivity,MainActivity::class.java))
                        finish()
                    }
                    else {
                        val message = it.exception!!.message.toString()
                        Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@SigninActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}

