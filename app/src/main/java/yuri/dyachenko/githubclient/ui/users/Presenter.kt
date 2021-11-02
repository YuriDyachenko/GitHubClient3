package yuri.dyachenko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.network.NetworkStatusObservable
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val dataProvider: DataProvider,
    private val cacheDataProvider: DataProvider,
    private val saveDataProvider: DataSaveProvider,
    private val networkStatusObservable: NetworkStatusObservable,
    private val router: Router
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
        router.navigateTo(Screens.user(userLogin))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        defaultProvider()
            .getUsers()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
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
                .subscribeOn(Schedulers.newThread())
                .subscribe()
                .autoDispose()
        }
    }
}