package yuri.dyachenko.githubclient.model

data class User(
    val login: String,
    var likes: Int = 0,
    var dislikes: Int = 0
)
