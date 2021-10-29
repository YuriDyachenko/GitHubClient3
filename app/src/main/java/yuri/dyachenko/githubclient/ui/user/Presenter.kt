package yuri.dyachenko.githubclient.ui.user

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.UsersRepo

class Presenter(
    private val usersRepo: UsersRepo,
    private val login: String
) : Contract.Presenter() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        disposables.add(
            usersRepo
                .getUserByLogin(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { viewState.setState(Contract.State.Success(it)) },
                    onError = { viewState.setState(Contract.State.Error(it)) }
                )
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}