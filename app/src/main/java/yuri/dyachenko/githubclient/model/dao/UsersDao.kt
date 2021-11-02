package yuri.dyachenko.githubclient.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.USERS_TABLE_NAME
import yuri.dyachenko.githubclient.model.User

@Dao
interface UsersDao {

    @Query("SELECT * FROM $USERS_TABLE_NAME")
    fun get(): Single<List<User>>

    @Insert(onConflict = REPLACE)
    fun add(users: List<User>): Completable
}