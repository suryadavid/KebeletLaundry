package com.kelompok3.kebeletlaundry

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragHome() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_home, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).menu.findItem(R.id.nav_home).isChecked = true

        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("TDG_APP", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putInt("active_fragment", 0)?.apply()

        view.findViewById<ConstraintLayout>(R.id.clFormalWash).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "FORMAL_WASH")
            val fragChooseItems = FragChooseItems()
            fragChooseItems.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.main_fragment, fragChooseItems)
                addToBackStack(null)
                commit()
            }
        }

        view.findViewById<ConstraintLayout>(R.id.clDryWash).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "DRY_WASH")
            val fragChooseItems = FragChooseItems()
            fragChooseItems.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.main_fragment, fragChooseItems)
                addToBackStack(null)
                commit()
            }
        }

        view.findViewById<ConstraintLayout>(R.id.clSteamIron).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "STEAM_IRON")
            val fragChooseItems = FragChooseItems()
            fragChooseItems.arguments = bundle
            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.main_fragment, fragChooseItems)
                addToBackStack(null)
                commit()
            }
        }
        return view
    }
}