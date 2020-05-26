package com.example.gojek.di

import com.example.gojek.trending.dimodules.TrendingActivityModule
import com.example.gojek.trending.views.activity.TrendingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [TrendingActivityModule::class])
    internal abstract fun provideTrendingActivity():TrendingActivity
}