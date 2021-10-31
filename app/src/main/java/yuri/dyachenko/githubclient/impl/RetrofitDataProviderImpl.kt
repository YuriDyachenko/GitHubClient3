package yuri.dyachenko.githubclient.impl

import io.reactivex.Maybe
import yuri.dyachenko.githubclient.api.GitHubApi
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.Repo

class RetrofitDataProviderImpl(private val gitHubApi: GitHubApi) : DataProvider {

    override fun getUsers() =
        gitHubApi
            .getUsers()

    override fun getRepos(userLogin: String) =
        gitHubApi
            .getRepos(userLogin)

    override fun getRepo(userLogin: String, repoName: String): Maybe<Repo> =
        gitHubApi
            .getRepo(userLogin, repoName)
            .toMaybe()
}