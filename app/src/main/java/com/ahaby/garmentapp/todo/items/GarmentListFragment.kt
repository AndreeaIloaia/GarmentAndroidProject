package com.ahaby.garmentapp.todo.items

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ahaby.garmentapp.R
import com.ahaby.garmentapp.auth.data.AuthRepository
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.core.Utils
import kotlinx.android.synthetic.main.fragment_item_list.*

class GarmentListFragment : Fragment() {
    private lateinit var garmentListAdapter: GarmentListAdapter
    private lateinit var garmentModel: GarmentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        if (!AuthRepository.isLoggedIn) {
            findNavController().navigate(R.id.LoginFragment)
            return;
        }
        setupItemList()
        fab.setOnClickListener {
            Log.v(TAG, "add new garment")
            findNavController().navigate(R.id.ItemEditFragment)
        }
        logout.setOnClickListener {
            Log.v(TAG, "LOGOUT")
            AuthRepository.logout()
            findNavController().navigate(R.id.LoginFragment)
        }

    }

    private fun setupItemList() {
        garmentListAdapter = GarmentListAdapter(this)
        item_list.adapter = garmentListAdapter
        garmentModel = ViewModelProvider(this).get(GarmentListViewModel::class.java)
        garmentModel.garments.observe(viewLifecycleOwner) { items ->
            Log.v(TAG, "update items")
            garmentListAdapter.garments = items
        }
        garmentModel.loading.observe(viewLifecycleOwner) { loading ->
            Log.i(TAG, "update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        }
        garmentModel.loadingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
        garmentModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}