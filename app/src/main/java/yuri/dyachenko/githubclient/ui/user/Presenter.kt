package yuri.dyachenko.githubclient.ui.user

import kotlinx.coroutines.*
import yuri.dyachenko.githubclient.App
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes

class Presenter(
    private val usersRepo: UsersRepo,
    private val login: String
) : Contract.Presenter(), CoroutineScope by MainScope() {

    override fun onFirstViewAttach() {
        getData()
    }

    override fun onError() {
        getData()
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)
        if (sometimes()) {
            viewState.setState(Contract.State.Error)
        } else {
            launch {
                viewState.setState(withContext(Dispatchers.IO) {
                    delay(App.SIMULATION_DELAY_MILLIS)
                    usersRepo.getUserByLogin(login)?.let { Contract.State.Success(it) }
                        ?: Contract.State.Error
                })
            }
        }
    }
}