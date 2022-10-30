package com.kelompok3.kebeletlaundry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.w3c.dom.Text

class AdapterOrderHistory(private var orders: List<GetOrderResponsePayloadItem>, private var fragmentManager: FragmentManager) : RecyclerView.Adapter<AdapterOrderHistory.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_orderid_value).text = orders[position]._id
            findViewById<TextView>(R.id.tv_createdat_value).text =
                orders[position].status.accepted.time

            var status = "NULL"
            if (orders[position].status.accepted.state) {
                status = "Order Accepted"
                if (orders[position].status.picked_up.state) {
                    status = "Picked up"
                    if (orders[position].status.processing.state) {
                        status = "Processing"
                        if (orders[position].status.delivered.state)
                            status = "Delivered"
                    }
                }
            }

            findViewById<TextView>(R.id.tv_status_value).text = status

            findViewById<ConstraintLayout>(R.id.cl_order_item).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("order", Gson().toJson(orders[position].status))
                val fragTrackYourOrder = FragTrackYourOrder()
                fragTrackYourOrder.arguments = bundle

                fragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                    )
                    replace(R.id.main_fragment, fragTrackYourOrder)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}