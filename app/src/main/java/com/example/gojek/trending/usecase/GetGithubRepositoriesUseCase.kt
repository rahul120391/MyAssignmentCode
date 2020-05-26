package com.example.gojek.trending.usecase

import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.networking.cleanarchitecturebase.UseCase
import com.example.gojek.trending.datasources.IGithubRepositoryDataSource
import com.example.gojek.trending.repositories.GithubDataRepository

class GetGithubRepositoriesUseCase(private val githubDataRepository: GithubDataRepository) :UseCase<GetGithubRepositoriesUseCase.RequestValues,GetGithubRepositoriesUseCase.ResponseValue>(){

    override fun executeUseCase(requestValues: RequestValues?) {
         githubDataRepository.getGithubRepoList(requestValues?.isPullToRefresh?:false,object : IGithubRepositoryDataSource.GetGithubDataRepository {
             override fun onSuccess(response: ArrayList<GithubAccountDataModelItem>) {
                 useCaseCallback?.onSuccess(ResponseValue(response))
             }

             override fun onError(message: String) {
                    useCaseCallback?.onError(message)
             }
         })   
    }
    class RequestValues(val isPullToRefresh:Boolean): UseCase.RequestValues
    class ResponseValue(val list: ArrayList<GithubAccountDataModelItem>) : UseCase.ResponseValue
}