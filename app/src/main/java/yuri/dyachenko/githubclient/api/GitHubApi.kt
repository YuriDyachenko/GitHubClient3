package yuri.dyachenko.githubclient.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.model.User

interface GitHubApi {

    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/users/{userlogin}/repos")
    fun getRepos(@Path("userlogin") userLogin: String): Single<List<Repo>>

    @GET("/repos/{userlogin}/{reponame}")
    fun getRepo(
        @Path("userlogin") userLogin: String,
        @Path("reponame") repoName: String
    ): Single<Repo>
}