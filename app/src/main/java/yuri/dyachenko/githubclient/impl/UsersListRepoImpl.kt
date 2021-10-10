package yuri.dyachenko.githubclient.impl

import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UsersRepo

class UsersListRepoImpl : UsersRepo {

    private val users = listOf(
        User("Ivanov"),
        User("Petrov"),
        User("Sidorov"),
        User("Antonov"),
        User("Bilan")
    )

    override fun getUsers() = users

    override fun getUserByLogin(login: String): User? =
        users.firstOrNull { user -> user.login == login }
}