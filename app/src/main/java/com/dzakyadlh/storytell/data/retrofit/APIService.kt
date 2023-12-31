package com.dzakyadlh.storytell.data.retrofit

import com.dzakyadlh.storytell.data.response.GetAllStoryResponse
import com.dzakyadlh.storytell.data.response.GetDetailStoryResponse
import com.dzakyadlh.storytell.data.response.LoginResponse
import com.dzakyadlh.storytell.data.response.NewStoryResponse
import com.dzakyadlh.storytell.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun newStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null
    ): NewStoryResponse

    @GET("stories")
    suspend fun getAllStory(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): GetAllStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String
    ): GetDetailStoryResponse

    @GET("stories")
    suspend fun getStoryWithLocation(
        @Query("location") location: Int = 1,
    ): GetAllStoryResponse
}