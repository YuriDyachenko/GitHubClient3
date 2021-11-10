package yuri.dyachenko.githubclient.di

import com.google.gson.Gson
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

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory
            .create(gson)

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory
            .create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(okHttpClient)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
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