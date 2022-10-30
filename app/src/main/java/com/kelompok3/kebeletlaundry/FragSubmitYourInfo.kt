package com.kelompok3.kebeletlaundry

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import retrofit2.HttpException
import retrofit2.create
import java.io.IOException

class FragSubmitYourInfo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_submit_your_info, container, false)
        val api = RetrofitInstance.getInstance().create(TdgApi::class.java)
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("TDG_APP", Context.MODE_PRIVATE)

        lifecycleScope.launchWhenCreated {
            val response = try {
                api.getProfile("Bearer ${sharedPreferences?.getString("jwt", "NULL")!!}")
            } catch (e: IOException) {
                Toast.makeText(activity, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }

            Log.i("HI", "${response.body().toString()} ${response.code()}")

            if (response.body() != null) {
                view.findViewById<EditText>(R.id.et_full_name).setText(response.body()!!.payload.name)
                view.findViewById<EditText>(R.id.et_address).setText(response.body()!!.payload.address)
                view.findViewById<EditText>(R.id.et_email).setText(response.body()!!.payload.email)
                view.findViewById<EditText>(R.id.et_phone).setText(response.body()!!.payload.phone)
            }
        }

        view.findViewById<ConstraintLayout>(R.id.navigate_back).setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        view.findViewById<ConstraintLayout>(R.id.place_order_btn).setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val address = view.findViewById<EditText>(R.id.et_address).text.toString()
                val response = try {
                    api.postNewOrder(
                        "Bearer ${sharedPreferences?.getString("jwt", "NULL")}",
                        PostOrderReq(address)
                    )
                } catch(e: IOException) {
                    Toast.makeText(activity, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                }

                if (response.body() != null)
                    Toast.makeText(activity, "Order has been placed \uD83C\uDF89", Toast.LENGTH_LONG).show()
            }

            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.main_fragment, FragOrderHistory())
                commit()
            }
        }

        return view
    }
}