package com.dzakyadlh.storytell.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dzakyadlh.storytell.data.Result
import com.dzakyadlh.storytell.data.paging.StoryPagingSource
import com.dzakyadlh.storytell.data.response.ErrorResponse
import com.dzakyadlh.storytell.data.response.ListStoryItem
import com.dzakyadlh.storytell.data.response.Story
import com.dzakyadlh.storytell.data.retrofit.APIService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private val apiService: APIService,
) {
    fun newStory(imageFile: File, description: String) = liveData {
        emit(Result.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.newStory(multipartBody, requestBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

//    fun getAllStory(): LiveData<Result<List<ListStoryItem>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val successResponse = apiService.getAllStory()
//            emit(Result.Success(successResponse.listStory))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
//            emit(Result.Error(errorResponse.message.toString()))
//        }
//    }

    fun getAllStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun getDetailStory(id: String): LiveData<Result<Story>> = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.getDetailStory(id)
            emit(Result.Success(successResponse.story))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

    fun getLocationStory(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.getStoryWithLocation()
            emit(Result.Success(successResponse.listStory))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(apiService: APIService) =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}