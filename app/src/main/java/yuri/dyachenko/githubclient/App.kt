package yuri.dyachenko.githubclient

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import yuri.dyachenko.githubclient.bus.EventBus
import yuri.dyachenko.githubclient.impl.UsersListRepoImpl
import yuri.dyachenko.githubclient.model.UsersRepo

class App : Application() {

    val usersRepo: UsersRepo = UsersListRepoImpl()

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder = cicerone.getNavigatorHolder()
    val router = cicerone.router

    val bus = EventBus()
}