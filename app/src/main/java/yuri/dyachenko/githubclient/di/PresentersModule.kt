package yuri.dyachenko.githubclient.di

import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.network.NetworkStatusObservable
import yuri.dyachenko.githubclient.ui.Screens
import yuri.dyachenko.githubclient.ui.users.Presenter
import javax.inject.Named

@Module
class PresentersModule {

    @Provides
    fun provideUsersPresenter(
        @Named("web") dataProvider: DataProvider,
        cacheDataProvider: DataProvider,
        saveDataProvider: DataSaveProvider,
        networkStatusObservable: NetworkStatusObservable,
        router: Router,
        screens: Screens
    ) = Presenter(
        dataProvider,
        cacheDataProvider,
        saveDataProvider,
        networkStatusObservable,
        router,
        screens
    )

    @Provides
    fun provideUserPresenter(
        @Named("web") dataProvider: DataProvider,
        cacheDataProvider: DataProvider,
        saveDataProvider: DataSaveProvider,
        networkStatusObservable: NetworkStatusObservable,
        router: Router,
        screens: Screens
    ) = yuri.dyachenko.githubclient.ui.user.Presenter(
        dataProvider,
        cacheDataProvider,
        saveDataProvider,
        networkStatusObservable,
        router,
        screens
    )

    @Provides
    fun provideRepoPresenter(
        @Named("web") dataProvider: DataProvider,
        cacheDataProvider: DataProvider,
        networkStatusObservable: NetworkStatusObservable
    ) = yuri.dyachenko.githubclient.ui.repo.Presenter(
        dataProvider,
        cacheDataProvider,
        networkStatusObservable
    )
}