package com.example.gojek.di

import com.example.gojek.ProjectApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [(AndroidInjectionModule::class), (AppModule::class),(ActivityBuilder::class)])
@Singleton
interface AppComponent:AndroidInjector<ProjectApp> {

    override fun inject(instance: ProjectApp)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ProjectApp): Builder
        fun build(): AppComponent
    }

}