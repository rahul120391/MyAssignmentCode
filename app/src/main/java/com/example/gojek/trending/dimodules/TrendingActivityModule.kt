package com.example.gojek.trending.dimodules

import com.example.gojek.ProjectApp
import com.example.gojek.database.DatabaseConstants
import com.example.gojek.database.RepositoryDatabase
import com.example.gojek.networking.cleanarchitecturebase.UseCaseHandler
import com.example.gojek.networking.retrofit.RetrofitServiceAnnotator
import com.example.gojek.trending.datasources.GithubRepositoriesLocalDataSource
import com.example.gojek.trending.datasources.GithubRepositoriesRemoteDataSource
import com.example.gojek.trending.repositories.GithubDataRepository
import com.example.gojek.trending.usecase.GetGithubRepositoriesUseCase
import com.example.gojek.trending.viewmodel.TrendingActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class TrendingActivityModule {

    @Provides
    internal fun provideGithubRemoteDataSource(retrofitServiceAnnotator: RetrofitServiceAnnotator) =
        GithubRepositoriesRemoteDataSource(retrofitServiceAnnotator)

    @Provides
    internal fun checkIfDatabaseExists(context: ProjectApp): Boolean =
        context.getDatabasePath(DatabaseConstants.DATABASE_NAME).exists()

    @Provides
    internal fun provideGithubLocalDataSource(
        repositoryDatabase: RepositoryDatabase,
        isDatabaseAvailable: Boolean
    ) =
        GithubRepositoriesLocalDataSource(repositoryDatabase.getTrendingDao(), isDatabaseAvailable)

    @Provides
    internal fun provideGithubDataRepository(
        remoteDataSource: GithubRepositoriesRemoteDataSource,
        localDataSource: GithubRepositoriesLocalDataSource
    ) = GithubDataRepository(localDataSource, remoteDataSource)

    @Provides
    internal fun provideGithubRepositoryUseCase(githubDataRepository: GithubDataRepository) =
        GetGithubRepositoriesUseCase(githubDataRepository)


    @Provides
    internal fun provideTrendingViewModel(
        useCaseHandler: UseCaseHandler?,
        getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase
    ) = TrendingActivityViewModel(useCaseHandler, getGithubRepositoriesUseCase)
}