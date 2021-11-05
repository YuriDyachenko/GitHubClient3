package yuri.dyachenko.githubclient.ui.repo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.network.NetworkStatusObservable

class Presenter(
    private val dataProvider: DataProvider,
    private val cacheDataProvider: DataProvider,
    private val networkStatusObservable: NetworkStatusObservable
) : Contract.Presenter() {

    private lateinit var userLogin: String
    private lateinit var repoName: String

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
        viewState.getData()
    }

    override fun onDataReady(userLogin: String, repoName: String) {
        this.userLogin = userLogin
        this.repoName = repoName
        getData()
    }

    override fun onError() = getData()

    override fun onUpdate() = getData()

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        defaultProvider()
            .getRepo(userLogin, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it, !isOnline)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }
}