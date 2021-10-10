package yuri.dyachenko.githubclient.model

interface UsersRepo {

    fun getUsers(): List<User>
    fun getUserByLogin(login: String): User?
}