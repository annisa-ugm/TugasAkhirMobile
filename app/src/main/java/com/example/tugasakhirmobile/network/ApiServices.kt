package com.example.tugasakhirmobile.network

import com.example.tugasakhirmobile.model.Furniture
import com.example.tugasakhirmobile.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    @GET("user")
    fun getAllUser(): Call<List<User>>

    @GET("user/{id")
    fun getSpecificUser(@Path("id") userId: String): Call<List<User>>

    @POST("user")
    fun postNewUser(@Body rawJson: RequestBody) : Call<User>

    @GET("furniture")
    fun getAllFurniture(): Call<List<Furniture>>

    @GET("furniture/{id")
    fun getSpecificFurniture(@Path("id") furnitureId: String): Call<List<Furniture>>

    @POST("furniture")
    fun postNewFurniture(@Body rawJson: RequestBody) : Call<Furniture>

    @POST("furniture/{id}")
    fun updateFurniture(@Path("id") furnitureId: String, @Body rawJson: RequestBody): Call<Furniture>

    @DELETE("furniture/{id}")
    fun deleteFurniture(@Path("id") furnitureId: String): Call<Furniture>

}