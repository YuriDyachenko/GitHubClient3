package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.model.RepoNotFoundException
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.model.DataProvider

class ListDataProviderImpl : DataProvider {

    private val users = listOf(
        User(1001, "sbecker", "https://avatars.githubusercontent.com/u/1001?v=4"),
        User(1002, "aharper", null),
        User(1003, "otto1218", "https://avatars.githubuserERRORcontent.com/u/93000001?v=4"),
        User(1005, "ernesto-jimenez", ""),
        User(1004, "adambair", "https://avatars.githubusercontent.com/u/1004?v=4")
    )

    private val repos = listOf(
        Repo(1001, 1, "ansible-postgresql-complete", 7),
        Repo(1001, 2, "asset_packager", 3),
        Repo(1002, 3, "eatingsafe", 0),
        Repo(1005, 4, "analytics-go", 3),
        Repo(1005, 5, "anonhttp", 6),
        Repo(1004, 6, "adambair.github.io", 5),
    )

    override fun getUsers(): Single<List<User>> = Single.just(users)

    override fun getReposByUserId(id: Int): Single<List<Repo>> =
        Single.just(repos.filter { it.userId == id })

    override fun getRepoById(id: Int): Maybe<Repo> = (
            repos.firstOrNull { it.id == id }
                ?.let { Maybe.just(it) }
                ?: Maybe.error(RepoNotFoundException())
            )
}