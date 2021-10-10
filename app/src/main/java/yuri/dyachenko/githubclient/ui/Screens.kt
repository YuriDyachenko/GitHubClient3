package yuri.dyachenko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import yuri.dyachenko.githubclient.ui.user.UserFragment
import yuri.dyachenko.githubclient.ui.users.UsersFragment

object Screens {

    fun users() = FragmentScreen { UsersFragment.newInstance() }
    fun user(login: String) = FragmentScreen { UserFragment.newInstance(login) }
}