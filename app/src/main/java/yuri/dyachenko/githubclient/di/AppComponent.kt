package yuri.dyachenko.githubclient.di

import dagger.Component
import yuri.dyachenko.githubclient.ui.MainActivity
import yuri.dyachenko.githubclient.ui.repo.RepoFragment
import yuri.dyachenko.githubclient.ui.user.UserFragment
import yuri.dyachenko.githubclient.ui.users.UsersFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        CiceroneModule::class,
        NetworkStatusModule::class,
        RoomModule::class,
        RetrofitModule::class,
        PresentersModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(repoFragment: RepoFragment)
}