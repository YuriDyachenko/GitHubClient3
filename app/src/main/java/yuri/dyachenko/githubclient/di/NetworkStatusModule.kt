package yuri.dyachenko.githubclient.di

import android.content.Context
import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.network.AndroidNetworkStatusObservable
import yuri.dyachenko.githubclient.network.NetworkStatusObservable

@Module
class NetworkStatusModule {

    @Provides
    fun provideNetworkStatus(context: Context): NetworkStatusObservable =
        AndroidNetworkStatusObservable(context)
}