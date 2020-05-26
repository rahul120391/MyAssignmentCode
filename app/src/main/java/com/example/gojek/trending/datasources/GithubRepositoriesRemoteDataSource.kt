package com.example.gojek.trending.datasources

import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.networking.retrofit.RetrofitServiceAnnotator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.schedulers.Schedulers

class GithubRepositoriesRemoteDataSource(private val retrofitServiceAnnotator: RetrofitServiceAnnotator) :
    IGithubRepositoryDataSource {


    override fun getGithubRepoList(isPullToRefresh:Boolean,callback: IGithubRepositoryDataSource.GetGithubDataRepository) {

        retrofitServiceAnnotator.fetchRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                it.body()?.let {
                    responseBody->
                    val bodyString=responseBody.string()
                    if(bodyString.startsWith("[") && bodyString.endsWith("]")){
                        val gSon = Gson()
                        val listType = object : TypeToken<Array<GithubAccountDataModelItem>>() {}.type
                        val response = ArrayList<GithubAccountDataModelItem>()
                        val res: Array<GithubAccountDataModelItem> = gSon.fromJson(bodyString, listType)
                        response.addAll(res)
                        callback.onSuccess(response)
                    }
                    else{
                        callback.onError(it.raw().message)
                    }
                } ?: kotlin.run {
                    callback.onError(it.raw().message)
                }
            }, {
                callback.onError(it.message?:"")
            })
    }
}