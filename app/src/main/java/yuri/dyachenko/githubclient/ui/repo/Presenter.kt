package yuri.dyachenko.githubclient.ui.repo

import io.reactivex.rxkotlin.subscribeBy
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.network.NetworkStatusObservable
import yuri.dyachenko.githubclient.scheduler.Schedulers

class Presenter(
    private val dataProvider: DataProvider,
    private val cacheDataProvider: DataProvider,
    private val networkStatusObservable: NetworkStatusObservable,
    private val schedulers: Schedulers
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
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it, !isOnline)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }
}