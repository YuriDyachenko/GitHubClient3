package yuri.dyachenko.githubclient.room

import androidx.room.Database
import androidx.room.RoomDatabase
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.dao.ReposDao
import yuri.dyachenko.githubclient.model.dao.UsersDao

@Database(
    exportSchema = false,
    entities = [User::class, Repo::class],
    version = 1
)
abstract class Storage : RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun reposDao(): ReposDao
}