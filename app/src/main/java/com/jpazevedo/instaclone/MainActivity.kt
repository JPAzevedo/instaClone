package com.jpazevedo.instaclone

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jpazevedo.instaclone.fragments.HomeFragment
import com.jpazevedo.instaclone.fragments.NotificationsFragment
import com.jpazevedo.instaclone.fragments.ProfileFragment
import com.jpazevedo.instaclone.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    // Check lateinit here: https://www.bignerdranch.com/blog/kotlin-when-to-use-lazy-or-lateinit/
    //private lateinit var message: TextView

    internal var selectedFragement : Fragment? = null
    internal var frameLayout : Int? = 0


    // This is a functional interface with only one argument. So, the lambda function can be called this way: interfaceName { arg -> logic of the function }
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when(item.itemId){
            R.id.nav_home -> {
                selectedFragement = HomeFragment()
            }
            R.id.nav_search -> {
                selectedFragement = SearchFragment()
            }
            R.id.nav_add_post -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                selectedFragement = NotificationsFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                selectedFragement = ProfileFragment()
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
        supportFragmentManager.beginTransaction().replace(frameLayout!!,selectedFragement!!).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        frameLayout = R.id.fragment_container
        supportFragmentManager.beginTransaction().replace(frameLayout!!,HomeFragment()).commit()

    }

}


