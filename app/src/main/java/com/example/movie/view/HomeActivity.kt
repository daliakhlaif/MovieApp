package com.example.movie.view


import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.databinding.ActivityHomeBinding
import com.example.movie.viewModel.HomeViewModel
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_UNLABELED


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()

    }

    private fun initialize(){
        setupBottomNavigationView()
        viewModel.onHomeSelected()
        viewModel.selectedFragment.observe(this) { fragment ->
            fragment?.let {
                replaceFragment(it)
            }
        }
        updateBottomNavColors(binding.bottomNavigationView.id)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.apply {
            labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item2 -> {
                        viewModel.onBookmarkSelected()
                        true
                    }
                    R.id.menu_item1 -> {
                        viewModel.onHomeSelected()
                        true
                    }
                    else -> false
                }
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