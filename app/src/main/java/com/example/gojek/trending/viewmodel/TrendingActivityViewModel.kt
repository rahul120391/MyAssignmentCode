package com.example.gojek.trending.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.gojek.base.BaseViewModel
import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.networking.cleanarchitecturebase.UseCase
import com.example.gojek.networking.cleanarchitecturebase.UseCaseHandler
import com.example.gojek.trending.usecase.GetGithubRepositoriesUseCase

class TrendingActivityViewModel(useCaseHandler: UseCaseHandler?,
                                private val githubRepositoriesUseCase: GetGithubRepositoriesUseCase):BaseViewModel(useCaseHandler) {
    val githubRepositoryLiveData=MutableLiveData<ArrayList<GithubAccountDataModelItem>>()
    val errorString = MutableLiveData<String>()
    fun getGithubRepositoryData(isPullToRefresh:Boolean){
           val requestValues= GetGithubRepositoriesUseCase.RequestValues(isPullToRefresh)
           useCaseHandler?.execute(
               githubRepositoriesUseCase,
               requestValues,
               object : UseCase.UseCaseCallback<GetGithubRepositoriesUseCase.ResponseValue> {
                   override fun onSuccess(response: GetGithubRepositoriesUseCase.ResponseValue) {
                        githubRepositoryLiveData.value = response.list
                   }

                   override fun onError(t: String?) {
                        errorString.value=t
                   }
               }
           )
    }

    fun sortByName(isPullToRefresh: Boolean){
        if(githubRepositoryLiveData.value?.isNotEmpty()==true && !isPullToRefresh){
            val list=githubRepositoryLiveData.value
            list?.sortBy { it.name }
            githubRepositoryLiveData.value=list
        }

    }
    fun sortByStar(isPullToRefresh: Boolean){
        if(githubRepositoryLiveData.value?.isNotEmpty()==true && !isPullToRefresh){
            val list=githubRepositoryLiveData.value
            list?.sortBy { it.stars }
            githubRepositoryLiveData.value=list
        }
    }
}