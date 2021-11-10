package yuri.dyachenko.githubclient.di

import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.ui.AppScreens
import yuri.dyachenko.githubclient.ui.Screens
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideScreens(): Screens = AppScreens()
}