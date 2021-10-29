package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UserNotFoundException
import yuri.dyachenko.githubclient.model.UsersRepo

class UsersListRepoImpl : UsersRepo {

    private val users = listOf(
        User(1001, "sbecker", "https://avatars.githubusercontent.com/u/1001?v=4"),
        User(1002, "aharper", null),
        User(1003, "otto1218", "https://avatars.githubuserERRORcontent.com/u/93000001?v=4"),
        User(1005, "ernesto-jimenez", ""),
        User(1004, "adambair", "https://avatars.githubusercontent.com/u/1004?v=4")
    )

    override fun getUsers(): Single<List<User>> = Single.just(users)

    override fun getUserByLogin(login: String): Maybe<User> = (
            users.firstOrNull { user -> user.login == login }
                ?.let { Maybe.just(it) }
                ?: Maybe.error(UserNotFoundException())
            )

}