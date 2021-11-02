package yuri.dyachenko.githubclient.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.REPOS_TABLE_NAME
import yuri.dyachenko.githubclient.model.Repo

@Dao
interface ReposDao {

    @Query("SELECT * FROM $REPOS_TABLE_NAME WHERE user_login = :userLogin")
    fun get(userLogin: String): Single<List<Repo>>

    @Query("SELECT * FROM $REPOS_TABLE_NAME WHERE user_login = :userLogin AND name = :repoName")
    fun get(userLogin: String, repoName: String): Maybe<Repo>

    @Insert(onConflict = REPLACE)
    fun add(repos: List<Repo>): Completable
}