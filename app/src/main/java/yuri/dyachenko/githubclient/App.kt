package yuri.dyachenko.githubclient

import android.app.Application
import yuri.dyachenko.githubclient.di.*

class App : Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        dagger = DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .ciceroneModule(CiceroneModule())
            .networkStatusModule(NetworkStatusModule())
            .roomModule(RoomModule())
            .retrofitModule(RetrofitModule())
            .schedulersModule(SchedulersModule())
            .presentersModule(PresentersModule())
            .build()

    }
}