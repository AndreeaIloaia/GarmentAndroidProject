package com.ahaby.garmentapp.todo.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ahaby.garmentapp.R
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.todo.data.Garment
import kotlinx.android.synthetic.main.fragment_item_edit.*

class GarmentEditFragment : Fragment() {
    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    private lateinit var viewModel: GarmentEditViewModel
    private var garmentId: String? = null
    private var garment: Garment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                garmentId = it.getString(ITEM_ID).toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_item_edit, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save item")
            val i = garment
            if (i != null) {
                Log.d(TAG, "ID:" + i._id)
                i.name = item_text.text.toString()
                i.material = item_material.text.toString()
                i.inaltime = item_inaltime.text.toString()
                i.latime = item_latime.text.toString()
                i.descriere = item_descriere.text.toString()
                viewModel.saveOrUpdateItem(i)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(GarmentEditViewModel::class.java)
        viewModel.fetching.observe(viewLifecycleOwner) { fetching ->
            Log.v(TAG, "update fetching")
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        }
        viewModel.fetchingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.completed.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().popBackStack()
            }
        }
        val id = garmentId
        if (id == null) {
            garment = Garment("", "", "", "", "", "")
        } else {
            viewModel.getGarmentById(id).observe(viewLifecycleOwner) {
                Log.v(TAG, "update items")
                if (it != null) {
                    garment = it
                    item_text.setText(it.name)
                    item_material.setText(it.material)
                    item_inaltime.setText(it.inaltime)
                    item_latime.setText(it.latime)
                    item_descriere.setText(it.descriere)
                }
            }
        }
    }
}
