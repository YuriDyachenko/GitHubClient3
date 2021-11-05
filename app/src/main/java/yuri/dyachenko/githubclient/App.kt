package yuri.dyachenko.githubclient

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yuri.dyachenko.githubclient.di.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                ciceroneModule,
                roomModule,
                retrofitModule,
                networkStatusModule,
                navigationModule,
                presentersModule
            )
        }
    }
}