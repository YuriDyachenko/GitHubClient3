package yuri.dyachenko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.rxkotlin.subscribeBy
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.network.NetworkStatusObservable
import yuri.dyachenko.githubclient.scheduler.Schedulers
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val dataProvider: DataProvider,
    private val cacheDataProvider: DataProvider,
    private val saveDataProvider: DataSaveProvider,
    private val networkStatusObservable: NetworkStatusObservable,
    private val router: Router,
    private val screens: Screens,
    private val schedulers: Schedulers
) : Contract.Presenter() {

    private fun subscribeOnNetworkState() {
        networkStatusObservable
            .isOnline()
            .subscribe { isOnline = it }
            .autoDispose()
    }

    private fun defaultProvider() =
        if (isOnline) dataProvider else cacheDataProvider

    override fun onFirstViewAttach() {
        subscribeOnNetworkState()
        getData()
    }

    override fun onError() = getData()

    override fun onUpdate() = getData()

    override fun onItemClicked(userLogin: String) {
        router.navigateTo(screens.user(userLogin))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        defaultProvider()
            .getUsers()
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .doOnSuccess { saveData(it) }
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it, !isOnline)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }

    private fun saveData(users: List<User>) {
        if (isOnline) {
            saveDataProvider
                .addUsers(users)
                .subscribeOn(schedulers.background())
                .subscribe()
                .autoDispose()
        }
    }
}