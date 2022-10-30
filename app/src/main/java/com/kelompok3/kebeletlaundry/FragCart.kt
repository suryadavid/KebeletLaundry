package com.kelompok3.kebeletlaundry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class FragCart : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_cart, container, false)

        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("TDG_APP", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putInt("active_fragment", 1)?.apply()

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).menu.findItem(R.id.nav_cart).isChecked = true

        val items = Array<TextView>(21) { i -> view.findViewById(intArrayOf(
            R.id.fw_tv_blazer_value,
            R.id.fw_tv_shirt_value,
            R.id.fw_tv_pant_value,
            R.id.fw_tv_saree_value,
            R.id.fw_tv_ladup_value,
            R.id.fw_tv_laddown_value,
            R.id.fw_tv_other_value,

            R.id.dw_tv_blazer_value,
            R.id.dw_tv_shirt_value,
            R.id.dw_tv_pant_value,
            R.id.dw_tv_saree_value,
            R.id.dw_tv_ladup_value,
            R.id.dw_tv_laddown_value,
            R.id.dw_tv_other_value,

            R.id.si_tv_blazer_value,
            R.id.si_tv_shirt_value,
            R.id.si_tv_pant_value,
            R.id.si_tv_saree_value,
            R.id.si_tv_ladup_value,
            R.id.si_tv_laddown_value,
            R.id.si_tv_other_value
        )[i])}

        val refreshView: (existingCart: String?) -> Unit = { existingCart ->
            if (!(existingCart == null || existingCart === "")) {
                val parsedExistingCart = Gson().fromJson(existingCart, CartInstance::class.java)

                for (i in 0..6) {
                    items[i].text =
                        parsedExistingCart.formal_wash.elementAt(i % 7).quantity.toString()
                }; for (i in 7..13) {
                    items[i].text = parsedExistingCart.dry_wash.elementAt(i % 7).quantity.toString()
                }; for (i in 14..20) {
                    items[i].text =
                        parsedExistingCart.steam_iron.elementAt(i % 7).quantity.toString()
                }
            }
        }

        val cleanView: () -> Unit = {
            for (i in 0..20)
                items[i].text = "0"
        }

        refreshView(sharedPreferences?.getString("cart", null))

        view.findViewById<ConstraintLayout>(R.id.clear_cart_btn).setOnClickListener {
            sharedPreferences?.edit()?.remove("cart")?.apply()
            cleanView()
            Toast.makeText(activity, "Cart has been emptied", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<ConstraintLayout>(R.id.confirm_items_btn).setOnClickListener {
            if (sharedPreferences?.getString("cart", null) == null)
                return@setOnClickListener Toast.makeText(activity, "Cart is empty, add a few items from the home screen and come back", Toast.LENGTH_SHORT).show()

            val api = RetrofitInstance.getInstance().create(TdgApi::class.java)

            lifecycleScope.launchWhenCreated {
                val response = try {
                    val cart = Gson().fromJson(sharedPreferences?.getString("cart", null), CartInstance::class.java)
                    api.postCart(
                        "Bearer ${sharedPreferences?.getString("jwt", "NULL")}",
                        CartInstance(cart.dry_wash, cart.formal_wash, cart.steam_iron)
                    )
                } catch(e: IOException) {
                    Toast.makeText(activity, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                }

                sharedPreferences.edit().remove("cart").apply()
                Toast.makeText(activity, "Items added to cart", Toast.LENGTH_SHORT).show()
            }

            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.main_fragment, FragSubmitYourInfo())
                addToBackStack(null)
                commit()
            }
        }

        return view
    }
}