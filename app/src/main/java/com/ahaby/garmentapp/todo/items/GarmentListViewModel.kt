package com.ahaby.garmentapp.todo.items

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.todo.data.Garment
import com.ahaby.garmentapp.todo.data.GarmentRepository
import com.ahaby.garmentapp.todo.data.local.TodoDatabase
import com.ahaby.garmentapp.core.Result
import kotlinx.coroutines.launch

class GarmentListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val garments: LiveData<List<Garment>>
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    val garmentRepository: GarmentRepository
    init {
        val garmentDao = TodoDatabase.getDatabase(application, viewModelScope).garmentDao()
        garmentRepository = GarmentRepository(garmentDao)
        garments = garmentRepository.items
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = garmentRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}
