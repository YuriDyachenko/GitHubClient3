package yuri.dyachenko.githubclient.ui.user

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes

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
                //для моделирования ошибки "User not found" иногда добавляем "1" к логину
                .getUserByLogin(login + if (sometimes()) "1" else "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { viewState.setState(Contract.State.Success(it)) },
                    onError = { viewState.setState(Contract.State.Error(it)) }
                )
        )
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}