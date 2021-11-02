package yuri.dyachenko.githubclient.model

import io.reactivex.Completable

interface DataSaveProvider {

    fun addUsers(users: List<User>): Completable
    fun addRepos(repos: List<Repo>): Completable
}