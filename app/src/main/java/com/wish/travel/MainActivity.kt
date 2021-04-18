package com.wish.travel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wish.travel.databinding.ActivityMainBinding
import com.wish.travel.ui.country.CountryFragment
import com.wish.travel.ui.explore.ExploreFragment
import com.wish.travel.ui.wishlist.WishlistFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_wishlist -> {
                    switchFragment(WishlistFragment())
                    true
                }
                R.id.action_countries -> {
                    switchFragment(ExploreFragment())
                    true
                }
                else -> false
            }
        }

//        switchFragment(WishlistFragment())
        switchFragment(CountryFragment())
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}