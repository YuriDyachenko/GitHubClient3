package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UserNotFoundException
import yuri.dyachenko.githubclient.model.UsersRepo

class UsersListRepoImpl : UsersRepo {

    private val users = listOf(
        User("Ivanov"),
        User("Petrov"),
        User("Sidorov"),
        User("Antonov"),
        User("Bilan")
    )

    override fun getUsers(): Single<List<User>> = Single.just(users)

    override fun getUserByLogin(login: String): Maybe<User> = (
            users.firstOrNull { user -> user.login == login }
                ?.let { Maybe.just(it) }
                ?: Maybe.error(UserNotFoundException())
            )

}