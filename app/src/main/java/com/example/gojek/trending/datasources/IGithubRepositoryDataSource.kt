package com.example.gojek.trending.datasources

import com.example.gojek.model.GithubAccountDataModelItem

interface IGithubRepositoryDataSource {

    interface GetGithubDataRepository{
        fun onSuccess(response:ArrayList<GithubAccountDataModelItem>)
        fun onError(message:String){}
    }

    fun getGithubRepoList(isPullToRefresh:Boolean,callback:GetGithubDataRepository)
}