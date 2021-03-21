package com.jpazevedo.instaclone

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.signup_link_btn
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signin_link_btn.setOnClickListener {
            finish()
        }

        signup_btn.setOnClickListener{v : View ->
            createAccount()
        }


    }

    private fun createAccount() {
        val fullName = fullname_signup.text.toString()
        val userName = username_signup.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"full name is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"username is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password is required.", Toast.LENGTH_LONG).show()

            else -> {
                val progressDialog = ProgressDialog(this@SignupActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait. This may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        saveUserInfo(fullName,userName,email,progressDialog)
                    }
                    else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }

    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDialog: ProgressDialog) {
        var currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap.put("uid",currentUserID)
        userMap.put("fullname",fullName)
        userMap.put("username",userName)
        userMap.put("email",email)
        userMap.put("bio","this is my bio.")
        userMap.put("image","https://firebasestorage.googleapis.com/v0/b/instagram-clone-app-4e005.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=09b8e7aa-9258-40be-9df8-019febdb62e2")

        usersRef.child(currentUserID).setValue(userMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this,"Account has been created successfully.", Toast.LENGTH_LONG).show()

                val intent = Intent(this@SignupActivity,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            else {
                val message = task.exception!!.toString()
                Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG)
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }

    }
}