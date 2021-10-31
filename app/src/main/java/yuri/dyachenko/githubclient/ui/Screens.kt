package yuri.dyachenko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import yuri.dyachenko.githubclient.ui.repo.RepoFragment
import yuri.dyachenko.githubclient.ui.user.UserFragment
import yuri.dyachenko.githubclient.ui.users.UsersFragment

object Screens {

    fun users() = FragmentScreen { UsersFragment.newInstance() }
    fun user(login: String, id: Int) = FragmentScreen { UserFragment.newInstance(login, id) }
    fun repo(id: Int) = FragmentScreen { RepoFragment.newInstance(id) }
}