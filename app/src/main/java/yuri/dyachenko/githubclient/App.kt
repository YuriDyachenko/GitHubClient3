package yuri.dyachenko.githubclient

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import yuri.dyachenko.githubclient.impl.ListDataProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider

class App : Application() {

    val dataProvider: DataProvider = ListDataProviderImpl()

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder = cicerone.getNavigatorHolder()
    val router = cicerone.router
}