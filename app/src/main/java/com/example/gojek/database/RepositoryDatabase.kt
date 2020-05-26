package com.example.gojek.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gojek.database.DatabaseConstants.DATABASE_VERSION
import com.example.gojek.model.GithubAccountDataModel
import com.example.gojek.trending.trendingdatabase.TrendingDao
import com.example.gojek.typeconverters.DateTypeConverter
import com.example.gojek.typeconverters.RepositoryObjectConverter

@Database(entities = [GithubAccountDataModel::class],exportSchema = false,version = DATABASE_VERSION)
@TypeConverters(RepositoryObjectConverter::class,DateTypeConverter::class)
abstract class RepositoryDatabase():RoomDatabase() {
    abstract fun getTrendingDao():TrendingDao
}