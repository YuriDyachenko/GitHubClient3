package yuri.dyachenko.githubclient.model

import io.reactivex.Maybe
import io.reactivex.Single

interface UsersRepo {

    fun getUsers(): Single<List<User>>
    fun getUserByLogin(login: String): Maybe<User>
    fun likeUserByLogin(login: String): Maybe<User>
    fun dislikeUserByLogin(login: String): Maybe<User>
}