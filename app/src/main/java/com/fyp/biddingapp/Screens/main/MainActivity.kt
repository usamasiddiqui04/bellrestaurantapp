package com.fyp.biddingapp.Screens.main

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fyp.biddingapp.R
import com.fyp.biddingapp.fragments.BidFragment
import com.fyp.biddingapp.fragments.HomeFragment
import com.fyp.biddingapp.fragments.SettingFragment
import com.fyp.biddingapp.fragments.WishlistFragment
import kotlinx.android.synthetic.main.activity_main.*
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.jetbrains.annotations.NotNull


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        if (!SharedPreferenceManager.getInstance(this).loggedin()) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//            return
//        }
        if (savedInstanceState == null) {

            animatedBottomBar.selectTabById(R.id.home, true)
            val fragmentManager = supportFragmentManager
            val homeFragment = HomeFragment()
            fragmentManager.beginTransaction().replace(R.id.frame_container, homeFragment)
                    .commit()
        }



        animatedBottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(lastIndex: Int, @Nullable lastTab: AnimatedBottomBar.Tab?, newIndex: Int, @NotNull newTab: AnimatedBottomBar.Tab) {
                var fragment: Fragment? = null
                when (newTab.id) {
                    R.id.home -> {
                        fragment = HomeFragment()
                        toolbar.title = "Home"
                    }
                    R.id.bids -> {
                        fragment = BidFragment()
                        toolbar.title = "Bids"
                    }
                    R.id.favourite -> {
                        fragment = WishlistFragment()
                        toolbar.title = "Favourites"
                    }
                    R.id.settings -> {
                        fragment = SettingFragment()
                        toolbar.title = "Settings"
                    }
                }
                if (fragment != null) {
                    val fragmentManager = supportFragmentManager
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                            .commit()
                }
            }
        })

    }

}