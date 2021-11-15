package yuri.dyachenko.githubclient.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import yuri.dyachenko.githubclient.api.GitHubApi
import yuri.dyachenko.githubclient.impl.RetrofitDataProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val baseUrl = "https://api.github.com"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    .build()
            )
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .create()
            )
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .create()
                    )
            )
            .build()

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi =
        retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    @Named("web")
    fun provideDataProvider(gitHubApi: GitHubApi): DataProvider =
        RetrofitDataProviderImpl(gitHubApi)
}