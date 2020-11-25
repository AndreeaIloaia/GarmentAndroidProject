package com.ahaby.garmentapp.todo.data.remote

import com.ahaby.garmentapp.core.Api
import com.ahaby.garmentapp.todo.data.Garment
import retrofit2.http.*

object GarmentApi {
    interface Service {
        @GET("/api/garments")
        suspend fun find(): List<Garment>

        @GET("/api/garments/{id}")
        suspend fun read(@Path("id") garmentId: String): Garment;

        @Headers("Content-Type: application/json")
        @POST("/api/garments/")
        suspend fun create(@Body garment: Garment): Garment

        @Headers("Content-Type: application/json")
        @PUT("/api/garments/{id}")
        suspend fun update(@Path("id") garmentId: String, @Body garment: Garment): Garment
    }
    val service: Service = Api.retrofit.create(Service::class.java)
}