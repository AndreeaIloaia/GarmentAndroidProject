package com.ahaby.garmentapp.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.ahaby.garmentapp.core.Result
import com.ahaby.garmentapp.todo.data.local.GarmentDao
import com.ahaby.garmentapp.todo.data.remote.GarmentApi

class GarmentRepository(private val garmentDao: GarmentDao) {
    val items = garmentDao.getAll()

    suspend fun refresh(): Result<Boolean> {
        try {
            val garments = GarmentApi.service.find()
            for (item in garments) {
                garmentDao.insert(item)
            }
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
            garmentDao.insert(createdItem)
            return Result.Success(createdItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun update(garment: Garment): Result<Garment> {
        try {
            val updatedItem = GarmentApi.service.update(garment._id, garment)
            garmentDao.update(updatedItem)
            return Result.Success(updatedItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }
}