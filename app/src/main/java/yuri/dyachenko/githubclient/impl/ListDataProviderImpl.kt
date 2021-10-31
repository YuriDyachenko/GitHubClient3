package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import io.reactivex.Single
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.model.RepoNotFoundException
import yuri.dyachenko.githubclient.model.User

class ListDataProviderImpl : DataProvider {

    private val users = listOf(
        User(1001, "sbecker", "https://avatars.githubusercontent.com/u/1001?v=4"),
        User(1002, "aharper", null),
        User(1003, "otto1218", "https://avatars.githubuserERRORcontent.com/u/93000001?v=4"),
        User(1005, "ernesto-jimenez", ""),
        User(1004, "adambair", "https://avatars.githubusercontent.com/u/1004?v=4")
    )

    private val repos = listOf(
        Repo("sbecker", 1, "ansible-postgresql-complete", 7),
        Repo("sbecker", 2, "asset_packager", 3),
        Repo("aharper", 3, "eatingsafe", 0),
        Repo("ernesto-jimenez", 4, "analytics-go", 3),
        Repo("ernesto-jimenez", 5, "anonhttp", 6),
        Repo("adambair", 6, "adambair.github.io", 5),
    )

    override fun getUsers(): Single<List<User>> = Single.just(users)

    override fun getRepos(userLogin: String): Single<List<Repo>> =
        Single.just(repos.filter { it.userLogin == userLogin })

    override fun getRepo(userLogin: String, repoName: String): Maybe<Repo> = (
            repos.firstOrNull { it.userLogin == userLogin && it.name == repoName }
                ?.let { Maybe.just(it) }
                ?: Maybe.error(RepoNotFoundException())
            )
}