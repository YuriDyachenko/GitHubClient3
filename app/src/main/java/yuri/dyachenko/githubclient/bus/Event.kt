package yuri.dyachenko.githubclient.bus

import yuri.dyachenko.githubclient.model.User

sealed class Event {
    data class UserUpdate(val user: User) : Event()
    object UsersUpdate : Event()
}