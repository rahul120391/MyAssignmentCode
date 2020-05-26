package com.example.gojek.trending.datasources

import com.example.gojek.model.GithubAccountDataModel
import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.trending.trendingdatabase.TrendingDao
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GithubRepositoriesLocalDataSource(
    private val trendingDao:TrendingDao,
    private val isDatabaseAvailable: Boolean
) :
    IGithubRepositoryDataSource {

    override fun getGithubRepoList(isPullToRefresh:Boolean,callback: IGithubRepositoryDataSource.GetGithubDataRepository) {

        val list = ArrayList<GithubAccountDataModelItem>()
        if (isDatabaseAvailable) {
            val data = trendingDao.getDataFromDataBase()
            val milliSeconds = (Date().time.minus(data?.dataStoreTime?.time ?: 0L))
            if (TimeUnit.MILLISECONDS.toHours(milliSeconds) <= 2) {
                data?.repositoryList?.let { list.addAll(it) }
            }
        }

        callback.onSuccess(list)
    }

    fun addDataToDatabase(githubAccountDataModelList: ArrayList<GithubAccountDataModelItem>) {
        val githubAccountDataModel = GithubAccountDataModel()
        githubAccountDataModel.repositoryList = githubAccountDataModelList
        githubAccountDataModel.dataStoreTime = Date()
        trendingDao.insertGithubRepositoryObject(githubAccountDataModel)
        val data = trendingDao.getDataFromDataBase()
        data?.repositoryList?.forEach {
            println("name is ${it.name}")
        }

    }

    fun clearTable() {
        trendingDao.deleteDataFromTable()
    }
}