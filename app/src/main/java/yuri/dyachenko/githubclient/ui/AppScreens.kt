package yuri.dyachenko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import yuri.dyachenko.githubclient.ui.repo.RepoFragment
import yuri.dyachenko.githubclient.ui.user.UserFragment
import yuri.dyachenko.githubclient.ui.users.UsersFragment

class AppScreens : Screens {

    override fun users() = FragmentScreen { UsersFragment.newInstance() }

    override fun user(userLogin: String) = FragmentScreen { UserFragment.newInstance(userLogin) }

    override fun repo(userLogin: String, repoName: String) =
        FragmentScreen { RepoFragment.newInstance(userLogin, repoName) }
}