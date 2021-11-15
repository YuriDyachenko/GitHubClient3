package yuri.dyachenko.githubclient.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import yuri.dyachenko.githubclient.impl.StorageDataProviderImpl
import yuri.dyachenko.githubclient.impl.StorageDataSaveProviderImpl
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.room.Storage
import javax.inject.Singleton

@Module
class RoomModule {

    private val dbName = "storage.db"

    @Provides
    @Singleton
    fun provideStorage(context: Context): Storage =
        Room.databaseBuilder(context, Storage::class.java, dbName)
            .build()

    @Provides
    fun provideDataProvider(storage: Storage): DataProvider =
        StorageDataProviderImpl(storage)

    @Provides
    fun provideDataSaveProvider(storage: Storage): DataSaveProvider =
        StorageDataSaveProviderImpl(storage)
}