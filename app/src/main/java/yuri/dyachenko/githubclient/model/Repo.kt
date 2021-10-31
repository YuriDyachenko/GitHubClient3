package yuri.dyachenko.githubclient.model

data class Repo(
    val userId: Int,
    val id: Int,
    val name: String,
    val forksCount: Int
)
