package com.dzakyadlh.storytell.data.repository

import androidx.lifecycle.liveData
import com.dzakyadlh.storytell.data.Result
import com.dzakyadlh.storytell.data.response.NewStoryResponse
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
            val errorResponse = Gson().fromJson(errorBody, NewStoryResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

//    fun getAllStory():LiveData<Result<List<StoryEntity>>>{
//
//    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(apiService: APIService) =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}