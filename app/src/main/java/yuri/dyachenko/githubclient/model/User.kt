package yuri.dyachenko.githubclient.model

data class User(
    val id: Int,
    val login: String,
    val avatar_url: String?
)
