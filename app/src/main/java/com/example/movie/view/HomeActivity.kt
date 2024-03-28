package com.example.movie.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, homeFragment)
        fragmentTransaction.commit()
    }

    private fun initialize(){
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnItemSelectedListener { item ->
               true
        }
        replaceFragment(HomeFragment())
    }

}


