package com.kelompok3.kebeletlaundry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
class FragPayment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_payment, container, false)
        /* view.findViewById<ConstraintLayout>(R.id.navigate_back).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragPayment_to_fragSubmitYourInfo)
        }

        view.findViewById<ConstraintLayout>(R.id.item1).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragPayment_to_fragTrackYourOrder)
        }

        view.findViewById<ConstraintLayout>(R.id.item2).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragPayment_to_fragTrackYourOrder)
        }

        view.findViewById<ConstraintLayout>(R.id.item3).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragPayment_to_fragTrackYourOrder)
        }

        view.findViewById<ConstraintLayout>(R.id.item4).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragPayment_to_fragTrackYourOrder)
        } */
        return view
    }
}