package yuri.dyachenko.githubclient.room

import android.content.Context
import androidx.room.Room

object StorageFactory {

    private const val DB_NAME = "storage.db"

    fun create(context: Context): Storage =
        Room.databaseBuilder(context, Storage::class.java, DB_NAME)
            .build()
}