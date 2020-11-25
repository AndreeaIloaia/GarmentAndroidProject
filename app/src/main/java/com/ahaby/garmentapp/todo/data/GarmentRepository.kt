package com.ahaby.garmentapp.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.ahaby.garmentapp.core.Result
import com.ahaby.garmentapp.core.TAG
import com.ahaby.garmentapp.todo.data.local.GarmentDao
import com.ahaby.garmentapp.todo.data.remote.GarmentApi

class GarmentRepository(private val garmentDao: GarmentDao) {
    val items = garmentDao.getAll()

    suspend fun refresh(): Result<Boolean> {
        try {
            val garments = GarmentApi.service.find()
//            Log.v(garments.toString(), garments.toString());
            //if garments is null it might be because of the connection with server
            //we take the elements from local storage
            if(garments != null)
                garmentDao.deleteAll()
                for (item in garments)
                    garmentDao.insert(item)

            return Result.Success(true)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    fun getById(garmentId: String): LiveData<Garment> {
        return garmentDao.getById(garmentId)
    }

    suspend fun save(garment: Garment): Result<Garment> {
        try {
            val createdItem = GarmentApi.service.create(garment)
            if(createdItem != null)
                garmentDao.insert(createdItem)
            else
                garmentDao.insert(garment)
            Log.v(TAG, garment.name);
            return Result.Success(createdItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun update(garment: Garment): Result<Garment> {
        try {
            Log.d(TAG, "ID:" + garment._id)
            Log.d(TAG, "Entity:" + garment.toString())

            val updatedItem = GarmentApi.service.update(garment._id, garment)
            if(updatedItem != null)
                garmentDao.update(updatedItem)
            else
                garmentDao.insert(garment)
            return Result.Success(updatedItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }
}