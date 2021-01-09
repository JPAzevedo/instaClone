package com.jpazevedo.instaclone

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    // Check lateinit here: https://www.bignerdranch.com/blog/kotlin-when-to-use-lazy-or-lateinit/
    private lateinit var message: TextView

    // This is a functional interface with only one argument. So, the lambda function can be called this way: interfaceName { (arg) -> logic of the function }
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when(item.itemId){
            R.id.nav_home -> {
                message.setText("Home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                message.setText("Search")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add_post -> {
                message.setText("Post")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                message.setText("Notifications")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                message.setText("Profile")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.message)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}
