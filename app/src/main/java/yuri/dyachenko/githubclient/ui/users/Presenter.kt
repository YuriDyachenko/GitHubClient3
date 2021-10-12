package yuri.dyachenko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import yuri.dyachenko.githubclient.App
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val usersRepo: UsersRepo,
    private val router: Router
) : Contract.Presenter(), CoroutineScope by MainScope() {

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    override fun onUpdate() = getData()

    override fun onItemClicked(login: String) {
        router.navigateTo(Screens.user(login))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)
        if (sometimes()) {
            viewState.setState(Contract.State.Error)
        } else {
            launch {
                viewState.setState(withContext(Dispatchers.IO) {
                    delay(App.SIMULATION_DELAY_MILLIS)
                    Contract.State.Success(usersRepo.getUsers())
                })
            }
        }
    }
}