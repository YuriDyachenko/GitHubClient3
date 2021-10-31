package yuri.dyachenko.githubclient.model

import io.reactivex.Maybe
import io.reactivex.Single

interface DataProvider {

    fun getUsers(): Single<List<User>>
    fun getReposByUserId(id: Int): Single<List<Repo>>
    fun getRepoById(id: Int): Maybe<Repo>
}