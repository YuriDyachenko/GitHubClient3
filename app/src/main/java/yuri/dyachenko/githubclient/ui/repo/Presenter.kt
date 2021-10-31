package yuri.dyachenko.githubclient.ui.repo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.DataProvider

class Presenter(
    private val dataProvider: DataProvider,
    private val userLogin: String,
    private val repoName: String
) : Contract.Presenter() {

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        dataProvider
            .getRepo(userLogin, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }
}