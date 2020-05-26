package com.example.gojek.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gojek.database.DatabaseConstants.CURRENT_TIME
import com.example.gojek.database.DatabaseConstants.REPOSITORY_LIST
import com.example.gojek.database.DatabaseConstants.REPOSITORY_OBJECT_LIST_TABLE
import java.util.*

@Entity(tableName = REPOSITORY_OBJECT_LIST_TABLE)
class GithubAccountDataModel {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = CURRENT_TIME)
    var dataStoreTime: Date?=null

    @ColumnInfo(name = REPOSITORY_LIST)
    var repositoryList: List<GithubAccountDataModelItem>? = null
}