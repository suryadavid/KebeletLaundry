package com.kelompok3.kebeletlaundry

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setCurrentFragment(0, FragHome())
                R.id.nav_cart -> setCurrentFragment(1, FragCart())
                R.id.nav_orders -> setCurrentFragment(2, FragOrderHistory())
                else -> { setCurrentFragment(0, FragHome()) }
            }
            true
        }

        bottomNavigationView.setOnItemReselectedListener { }
    }

    private fun setCurrentFragment(fragmentIndex: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (fragmentIndex - getSharedPreferences("TDG_APP", MODE_PRIVATE).getInt("active_fragment", 0) > 0)
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
            else
                setCustomAnimations(
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_right,
                )

            val bundle = Bundle()
            bundle.putInt("index", fragmentIndex)
            fragment.arguments = bundle
            replace(R.id.main_fragment, fragment)
            commit()
        }
    }
}