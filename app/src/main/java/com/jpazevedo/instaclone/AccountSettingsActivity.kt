package com.jpazevedo.instaclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_settings.*

class AccountSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        logout_btn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@AccountSettingsActivity,SigninActivity::class.java))
            finish()
        }
    }
}