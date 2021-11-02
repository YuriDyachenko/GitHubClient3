package yuri.dyachenko.githubclient

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import yuri.dyachenko.githubclient.api.GitHubApiFactory
import yuri.dyachenko.githubclient.impl.RetrofitDataProviderImpl
import yuri.dyachenko.githubclient.impl.StorageDataProviderImpl
import yuri.dyachenko.githubclient.impl.StorageDataSaveProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.room.StorageFactory

class App : Application() {

    val dataProvider: DataProvider = RetrofitDataProviderImpl(GitHubApiFactory.create())
    val roomDataProvider: DataProvider by lazy {
        StorageDataProviderImpl(
            StorageFactory.create(app)
        )
    }
    val roomDataSaveProvider: DataSaveProvider by lazy {
        StorageDataSaveProviderImpl(
            StorageFactory.create(app)
        )
    }

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder = cicerone.getNavigatorHolder()
    val router = cicerone.router
}