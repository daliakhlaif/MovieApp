package com.example.movie.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.apply {
            labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
            itemIconTintList = null
            setOnItemSelectedListener { true }
        }
    }

}


