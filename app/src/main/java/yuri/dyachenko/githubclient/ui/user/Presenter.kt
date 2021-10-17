package yuri.dyachenko.githubclient.ui.user

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.bus.Event
import yuri.dyachenko.githubclient.bus.EventBus
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.sometimes

class Presenter(
    private val usersRepo: UsersRepo,
    private val login: String,
    private val bus: EventBus
) : Contract.Presenter() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    override fun onLike() {
        disposables.add(
            usersRepo
                .likeUserByLogin(login)
                .subscribeOn(Schedulers.io())
                .subscribe { bus.post(Event.UserUpdate(it)) }
        )
    }

    override fun onDislike() {
        disposables.add(
            usersRepo
                .dislikeUserByLogin(login)
                .subscribeOn(Schedulers.io())
                .subscribe { bus.post(Event.UserUpdate(it)) }
        )
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        disposables.add(
            usersRepo
                //для моделирования ошибки "User not found" иногда добавляем "1" к логину
                .getUserByLogin(login + if (sometimes()) "1" else "")
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