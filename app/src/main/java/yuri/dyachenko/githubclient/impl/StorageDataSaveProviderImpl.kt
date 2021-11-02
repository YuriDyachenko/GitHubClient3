package yuri.dyachenko.githubclient.impl

import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.room.Storage

class StorageDataSaveProviderImpl(private val storage: Storage) : DataSaveProvider {

    override fun addUsers(users: List<User>) =
        storage.usersDao().add(users)

    override fun addRepos(repos: List<Repo>) =
        storage.reposDao().add(repos)
}