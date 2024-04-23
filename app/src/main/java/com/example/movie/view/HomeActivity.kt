package com.example.movie.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationBarView


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()

    }

    private fun initialize(){
        setupBottomNavigationView()
        replaceFragment(HomeFragment())
       updateBottomNavColors(binding.bottomNavigationView.id)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }

    private fun showFragment(fragmentTag: String, fragmentFactory: () -> Fragment): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val existingFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (existingFragment != null) {
            fragmentTransaction.show(existingFragment)
        } else {
            fragmentTransaction.replace(binding.frameLayout.id, fragmentFactory(), fragmentTag)
        }
        fragmentTransaction.commit()
        return true
    }


    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.apply {
            labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
            setOnItemSelectedListener { menuItem ->
                val transactionSuccess = when (menuItem.itemId) {
                    R.id.menu_item2 -> showFragment(BookmarkFragment::class.java.simpleName) { BookmarkFragment() }
                    R.id.menu_item1 -> showFragment(HomeFragment::class.java.simpleName) { HomeFragment() }
                    else -> false
                }
                transactionSuccess
            }
        }
    }



    private fun updateBottomNavColors(selectedItemId: Int) {
        val defaultColor = ContextCompat.getColor(this, R.color.grey1)
        val selectedColor = ContextCompat.getColor(this, R.color.black)

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(selectedColor, defaultColor)
        )
        binding.bottomNavigationView.itemIconTintList = colorStateList
        binding.bottomNavigationView.menu.findItem(selectedItemId)?.isChecked = true
    }



}


