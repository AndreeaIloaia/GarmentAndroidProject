package com.ahaby.garmentapp.todo.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahaby.garmentapp.todo.data.Garment

@Dao
interface GarmentDao {
    @Query("SELECT * from garments ORDER BY name ASC")
    fun getAll(): LiveData<List<Garment>>

    @Query("SELECT * FROM garments WHERE _id=:id ")
    fun getById(id: String): LiveData<Garment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Garment)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Garment)

    @Query("DELETE FROM garments")
    suspend fun deleteAll()
}