package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.App
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes
import java.io.IOException
import java.util.concurrent.TimeUnit

class UsersListRepoImpl : UsersRepo {

    private val users = listOf(
        User("Ivanov"),
        User("Petrov"),
        User("Sidorov"),
        User("Antonov"),
        User("Bilan")
    )

    override fun getUsers(): Single<List<User>> =
        if (sometimes()) {
            Single.error(IOException())
        } else {
            Single.just(users)
        }.delay(App.SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    override fun getUserByLogin(login: String): Maybe<User> =
        if (sometimes()) {
            Maybe.error(IOException())
        } else {
            users.firstOrNull { user -> user.login == login }
                ?.let { Maybe.just(it) }
                ?: Maybe.empty()
        }.delay(App.SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)
}