package yuri.dyachenko.githubclient.di

import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.scheduler.DefaultSchedulers
import yuri.dyachenko.githubclient.scheduler.Schedulers

@Module
class SchedulersModule {

    @Provides
    fun provideNetworkStatus(): Schedulers = DefaultSchedulers()
}