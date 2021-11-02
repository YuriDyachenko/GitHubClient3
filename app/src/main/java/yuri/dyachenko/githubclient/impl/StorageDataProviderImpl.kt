package yuri.dyachenko.githubclient.impl

import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.room.Storage

class StorageDataProviderImpl(private val storage: Storage) : DataProvider {

    override fun getUsers() =
        storage
            .usersDao()
            .get()

    override fun getRepos(userLogin: String) =
        storage
            .reposDao()
            .get(userLogin)

    override fun getRepo(userLogin: String, repoName: String) =
        storage
            .reposDao()
            .get(userLogin, repoName)
}