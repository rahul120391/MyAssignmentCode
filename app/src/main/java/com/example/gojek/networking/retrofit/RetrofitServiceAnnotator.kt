package com.example.gojek.networking.retrofit

import com.example.gojek.model.GithubAccountDataModel
import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.networking.urls.RequestUrl.REPOSITORIES_URL
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitServiceAnnotator {

    @GET(REPOSITORIES_URL)
    fun fetchRepositories():Flowable<Response<ResponseBody>>
}