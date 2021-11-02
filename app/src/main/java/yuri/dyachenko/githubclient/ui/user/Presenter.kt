package yuri.dyachenko.githubclient.ui.user

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.network.NetworkStatusObservable
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val dataProvider: DataProvider,
    private val cacheDataProvider: DataProvider,
    private val saveDataProvider: DataSaveProvider,
    private val networkStatusObservable: NetworkStatusObservable,
    private val router: Router,
    private val userLogin: String
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

    override fun onItemClicked(userLogin: String, repoName: String) {
        router.navigateTo(Screens.repo(userLogin, repoName))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        defaultProvider()
            .getRepos(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { saveData(it) }
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it, !isOnline)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }

    private fun saveData(repos: List<Repo>) {
        if (isOnline) {
            repos.forEach { repo -> repo.userLogin = userLogin }
            saveDataProvider
                .addRepos(repos)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
                .autoDispose()
        }
    }
}