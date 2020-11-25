package com.ahaby.garmentapp.todo.item

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.todo.data.Garment
import com.ahaby.garmentapp.todo.data.GarmentRepository
import com.ahaby.garmentapp.todo.data.local.TodoDatabase
import com.ahaby.garmentapp.core.Result
import kotlinx.coroutines.launch

class GarmentEditViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    val garmentRepository: GarmentRepository

    init {
        val garmentDao = TodoDatabase.getDatabase(application, viewModelScope).garmentDao()
        garmentRepository = GarmentRepository(garmentDao)
    }

    fun getGarmentById(germentId: String): LiveData<Garment> {
        Log.v(TAG, "get Garment by Id")
        return garmentRepository.getById(germentId)
    }


    fun saveOrUpdateItem(garment: Garment) {
        viewModelScope.launch {
            Log.v(TAG, "saveOrUpdate garment...");
            mutableFetching.value = true
            mutableException.value = null
            val result: Result<Garment>
            if (garment._id.isNotEmpty()) {
                Log.d(TAG, "ID:" + garment._id)

                result = garmentRepository.update(garment)
            } else {
                result = garmentRepository.save(garment)
            }
            when(result) {
                is Result.Success -> {
                    Log.d(TAG, "saveOrUpdateItem succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "saveOrUpdateItem failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableCompleted.value = true
            mutableFetching.value = false
        }
    }
}
