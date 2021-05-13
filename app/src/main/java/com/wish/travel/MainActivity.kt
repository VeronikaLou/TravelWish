package com.wish.travel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.wish.travel.data.Country
import com.wish.travel.databinding.ActivityMainBinding
import com.wish.travel.ui.explore.ExploreFragment
import com.wish.travel.ui.wishlist.WishlistFragment

class MainActivity : AppCompatActivity(), Communicator {
    private val wishlistFragment: WishlistFragment = WishlistFragment()
    private val wishlistCountries: ArrayList<Country> = ArrayList()
    private var countries: ArrayList<Country> = ArrayList()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_wishlist -> {
                    switchFragment(wishlistFragment)
                    true
                }
                R.id.action_countries -> {
                    switchFragment(ExploreFragment())
                    true
                }
                else -> false
            }
        }

        switchFragment(ExploreFragment())
//        switchFragment(WishlistFragment())
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun passWishlistCountriesIndexes(position: Int) {
        val bundle = Bundle()
        val country = countries[position]
        if (!wishlistCountries.contains(country)) {
            country.wishlistOrder = wishlistCountries.size + 1
            wishlistCountries.add(country)
            Toast.makeText( this, "${country.name} added to the wishlist", Toast.LENGTH_SHORT).show()
        } else {
            wishlistCountries.remove(country)
        }
        bundle.putSerializable("wishlist", wishlistCountries)

        val transaction = this.supportFragmentManager.beginTransaction()
        wishlistFragment.arguments = bundle

        transaction.commit()
    }

    override fun passCountries(countries: ArrayList<Country>) {
        this.countries = countries
    }
}