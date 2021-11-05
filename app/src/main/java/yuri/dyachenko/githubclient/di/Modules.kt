package yuri.dyachenko.githubclient.di

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import yuri.dyachenko.githubclient.api.GitHubApi
import yuri.dyachenko.githubclient.impl.RetrofitDataProviderImpl
import yuri.dyachenko.githubclient.impl.StorageDataProviderImpl
import yuri.dyachenko.githubclient.impl.StorageDataSaveProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.network.AndroidNetworkStatusObservable
import yuri.dyachenko.githubclient.room.Storage
import yuri.dyachenko.githubclient.ui.AppScreens
import yuri.dyachenko.githubclient.ui.Screens

const val RETROFIT_NAMED = "web"

val ciceroneModule = module {
    single {
        Cicerone.create()
    }

    single {
        get<Cicerone<Router>>().router
    }

    single {
        get<Cicerone<Router>>().getNavigatorHolder()
    }
}

val retrofitModule = module {
    val baseUrl = "https://api.github.com"

    single {
        GsonBuilder()
            .create()
    }

    single {
        GsonConverterFactory
            .create(get())
    }

    single {
        RxJava2CallAdapterFactory
            .create()
    }

    single {
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single {
        get<Retrofit>().create(GitHubApi::class.java)
    }

    single<DataProvider>(named(RETROFIT_NAMED)) {
        RetrofitDataProviderImpl(get())
    }
}

val roomModule = module {
    val dbName = "storage.db"

    single {
        Room.databaseBuilder(androidContext(), Storage::class.java, dbName)
            .build()
    }

    factory<DataProvider> {
        StorageDataProviderImpl(get())
    }

    factory<DataSaveProvider> {
        StorageDataSaveProviderImpl(get())
    }
}

val networkStatusModule = module {
    factory {
        AndroidNetworkStatusObservable(androidContext())
    }
}

val navigationModule = module {
    single<Screens> {
        AppScreens()
    }
}