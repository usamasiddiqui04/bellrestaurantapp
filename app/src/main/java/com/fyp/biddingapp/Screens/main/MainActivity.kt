package com.fyp.biddingapp.Screens.main

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fyp.biddingapp.R
import com.fyp.biddingapp.fragments.BidFragment
import com.fyp.biddingapp.fragments.HomeFragment
import com.fyp.biddingapp.fragments.SettingFragment
import com.fyp.biddingapp.fragments.WishlistFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var drawable : Drawable? = null



    private var icons = intArrayOf(R.drawable.home, R.drawable.hammer, R.drawable.wishlist, R.drawable.settings)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        view_pager.adapter = ViewPagerFragmentAdapter(this)


        TabLayoutMediator(
                tab_layout, view_pager
        ) { tab: TabLayout.Tab, position: Int ->
            drawable = resources.getDrawable(icons[position])
            tab.icon = drawable
        }.attach()
    }

    private class ViewPagerFragmentAdapter(@NonNull fragmentActivity: FragmentActivity?) :
            FragmentStateAdapter(fragmentActivity!!) {
        @NonNull
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return HomeFragment()
                1 -> return BidFragment()
                2 -> return WishlistFragment()
                3 -> return SettingFragment()
            }
            return HomeFragment()
        }

        override fun getItemCount(): Int {
            return 4
        }
    }

}