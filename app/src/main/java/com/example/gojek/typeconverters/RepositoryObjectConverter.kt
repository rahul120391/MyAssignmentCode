package com.example.gojek.typeconverters

import androidx.room.TypeConverter
import com.example.gojek.model.GithubAccountDataModelItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RepositoryObjectConverter {

    private val gson = Gson()
    @TypeConverter
    fun stringToSomeObject(data: String?): List<GithubAccountDataModelItem> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<GithubAccountDataModelItem>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectToString(listObject: List<GithubAccountDataModelItem>): String {
        return gson.toJson(listObject)
    }

}