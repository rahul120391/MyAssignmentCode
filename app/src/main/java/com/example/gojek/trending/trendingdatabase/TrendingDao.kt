package com.example.gojek.trending.trendingdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gojek.database.DatabaseConstants
import com.example.gojek.database.DatabaseConstants.REPOSITORY_OBJECT_LIST_TABLE
import com.example.gojek.model.GithubAccountDataModel

@Dao
interface TrendingDao {

    @Insert
    fun insertGithubRepositoryObject(githubAccountDataModel: GithubAccountDataModel):Long

    @Query("SELECT * FROM $REPOSITORY_OBJECT_LIST_TABLE")
    fun getDataFromDataBase():GithubAccountDataModel?

    @Query("DELETE FROM $REPOSITORY_OBJECT_LIST_TABLE")
    fun deleteDataFromTable()

}