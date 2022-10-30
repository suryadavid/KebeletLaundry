package com.kelompok3.kebeletlaundry

import retrofit2.Response
import retrofit2.http.*

interface TdgApi {

    @POST("login")
    suspend fun postCredentials(@Body credentials: LoginCredentials) : Response<LoginResponse>

    @POST("cart")
    suspend fun postCart(@Header("Authorization") jwt: String, @Body cartInstance: CartInstance) : Response<PostCartResponse>

    @GET("profile")
    suspend fun getProfile(@Header("Authorization") jwt: String) : Response<GetProfileResponse>

    @POST("new-order")
    suspend fun postNewOrder(@Header("Authorization") jwt: String, @Body postOrderReq: PostOrderReq) : Response<GenericResponse>

    @GET("order-history")
    suspend fun getOrderHistory(@Header("Authorization") jwt: String) : Response<GetOrdersResponse>

    @POST("register")
    suspend fun postRegister(@Body postRegister: PostRegister) : Response<GenericResponse>
}