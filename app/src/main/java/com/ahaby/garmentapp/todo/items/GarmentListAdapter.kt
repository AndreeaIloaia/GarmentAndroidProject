package com.ahaby.garmentapp.todo.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ahaby.garmentapp.R
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.todo.data.Garment
import com.ahaby.garmentapp.todo.item.GarmentEditFragment
import kotlinx.android.synthetic.main.view_item.view.*

class GarmentListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<GarmentListAdapter.ViewHolder>() {

    var garments = emptyList<Garment>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener;

    init {
        onItemClick = View.OnClickListener { view ->
            val garment = view.tag as Garment
            fragment.findNavController().navigate(R.id.ItemEditFragment, Bundle().apply {
                putString(GarmentEditFragment.ITEM_ID, garment._id)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val item = garments[position]
        holder.itemView.tag = item
        holder.textView.text = item.name
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = garments.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.text
    }
}
