package yuri.dyachenko.githubclient.model

import io.reactivex.Maybe
import io.reactivex.Single

interface DataProvider {

    fun getUsers(): Single<List<User>>
    fun getRepos(userLogin: String): Single<List<Repo>>
    fun getRepo(userLogin: String, repoName: String): Maybe<Repo>
}