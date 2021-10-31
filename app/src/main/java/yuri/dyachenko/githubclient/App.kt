package yuri.dyachenko.githubclient

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import yuri.dyachenko.githubclient.api.GitHubApiFactory
import yuri.dyachenko.githubclient.impl.RetrofitDataProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider

class App : Application() {

    val dataProvider: DataProvider = RetrofitDataProviderImpl(GitHubApiFactory.create())

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder = cicerone.getNavigatorHolder()
    val router = cicerone.router
}