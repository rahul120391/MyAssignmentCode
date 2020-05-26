package com.example.gojek.trending.repositories

import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.trending.datasources.GithubRepositoriesLocalDataSource
import com.example.gojek.trending.datasources.GithubRepositoriesRemoteDataSource
import com.example.gojek.trending.datasources.IGithubRepositoryDataSource

class GithubDataRepository(
    private val localDataSource: GithubRepositoriesLocalDataSource,
    private val remoteDataSource: GithubRepositoriesRemoteDataSource
) : IGithubRepositoryDataSource {
    override fun getGithubRepoList(
        isPullToRefresh: Boolean,
        callback: IGithubRepositoryDataSource.GetGithubDataRepository
    ) {
        if (isPullToRefresh) {
            fetchDataFromServer(isPullToRefresh,callback)
        } else {
            localDataSource.getGithubRepoList(isPullToRefresh,object :
                IGithubRepositoryDataSource.GetGithubDataRepository {
                override fun onSuccess(response: ArrayList<GithubAccountDataModelItem>) {
                    if (response.isNotEmpty()) {
                        callback.onSuccess(response)
                    } else {
                        fetchDataFromServer(isPullToRefresh,callback)
                    }
                }
            })
        }

    }

    private fun fetchDataFromServer(isPullToRefresh: Boolean,callback: IGithubRepositoryDataSource.GetGithubDataRepository) {
        remoteDataSource.getGithubRepoList(isPullToRefresh,object :
            IGithubRepositoryDataSource.GetGithubDataRepository {
            override fun onSuccess(response: ArrayList<GithubAccountDataModelItem>) {
                localDataSource.apply {
                    clearTable()
                    addDataToDatabase(response)
                }
                callback.onSuccess(response)
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }

}