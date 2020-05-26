package com.example.gojek.di

import android.app.Application
import androidx.room.Room
import com.example.gojek.ProjectApp
import com.example.gojek.database.DatabaseConstants
import com.example.gojek.database.DatabaseConstants.DATABASE_NAME
import com.example.gojek.database.RepositoryDatabase
import com.example.gojek.networking.cleanarchitecturebase.UseCaseHandler
import com.example.gojek.networking.retrofit.RetrofitClient
import com.example.gojek.networking.retrofit.RetrofitServiceAnnotator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideRetrofitServiceAnnotator(): RetrofitServiceAnnotator =
        RetrofitClient.createRetrofitService()

    @Provides
    @Singleton
    internal fun provideUseCaseHandlerInstance() = UseCaseHandler.getInstance()

    @Provides
    @Singleton
    internal fun provideRepositoryDatabase(context:ProjectApp):RepositoryDatabase =
        Room.databaseBuilder(context,RepositoryDatabase::class.java,DATABASE_NAME)
            .build()
}