package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.SometimesException
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.UserNotFoundException
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes
import java.util.concurrent.TimeUnit

const val SIMULATION_DELAY_MILLIS = 1_000L

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
        }.delay(SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    override fun getUserByLogin(login: String): Maybe<User> = (
            users.firstOrNull { user -> user.login == login }
                ?.let {
                    if (sometimes()) {
                        Maybe.error(SometimesException())
                    } else {
                        Maybe.just(it)
                    }
                }
                ?: Maybe.error(UserNotFoundException())
            ).delay(SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    override fun likeUserByLogin(login: String): Maybe<User> = (
            users.firstOrNull { user -> user.login == login }
                ?.let {
                    it.likes++
                    Maybe.just(it)
                }
                ?: Maybe.error(UserNotFoundException())
            ).delay(SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    override fun dislikeUserByLogin(login: String): Maybe<User> = (
            users.firstOrNull { user -> user.login == login }
                ?.let {
                    it.dislikes++
                    Maybe.just(it)
                }
                ?: Maybe.error(UserNotFoundException())
            ).delay(SIMULATION_DELAY_MILLIS, TimeUnit.MILLISECONDS)
}