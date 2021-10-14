package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.App
import yuri.dyachenko.githubclient.model.SometimesException
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UserNotFoundException
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes
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
            //для моделирования случайной ошибки
            Single.error(SometimesException())
        } else {
            Single.just(users)
        }.delay(App.SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    override fun getUserByLogin(login: String): Maybe<User> =
        if (sometimes()) {
            //для моделирования случайной ошибки
            Maybe.error(SometimesException())
        } else {
            users.firstOrNull { user -> user.login == login }
                ?.let { Maybe.just(it) }
                ?: Maybe.error(UserNotFoundException())
        }.delay(App.SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)
}