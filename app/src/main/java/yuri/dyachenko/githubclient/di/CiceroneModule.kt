package yuri.dyachenko.githubclient.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.ui.AppScreens
import yuri.dyachenko.githubclient.ui.Screens
import javax.inject.Singleton

@Module
class CiceroneModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    @Singleton
    fun provideNavigationHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideScreens(): Screens = AppScreens()
}