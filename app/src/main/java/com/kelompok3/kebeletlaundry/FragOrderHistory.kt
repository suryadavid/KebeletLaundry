package com.kelompok3.kebeletlaundry

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.HttpException
import java.io.IOException

class FragOrderHistory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_order_history, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).menu.findItem(R.id.nav_orders).isChecked = true

        val api = RetrofitInstance.getInstance().create(TdgApi::class.java)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("TDG_APP", Context.MODE_PRIVATE)

        sharedPreferences?.edit()?.putInt("active_fragment", 2)?.apply()

        lifecycleScope.launchWhenCreated {
            val response = try {
                api.getOrderHistory("Bearer ${sharedPreferences?.getString("jwt", "NULL")}")
            } catch (e: IOException) {
                Toast.makeText(
                    activity,
                    "IOException, you might not have internet connection",
                    Toast.LENGTH_SHORT
                ).show()
                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }
            Log.i("hi", "${response.body().toString()} ${response.code()}")

            if (response.body() != null) {
                val adapter = AdapterOrderHistory(response.body()!!.payload, parentFragmentManager)
                val rv = view.findViewById<RecyclerView>(R.id.recycler_view)
                rv.layoutManager = LinearLayoutManager(activity)
                rv.adapter = adapter
            }
        }

        view.findViewById<ConstraintLayout>(R.id.navigate_back).setOnClickListener{
            Log.i("tdg", "back pressed")
            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}