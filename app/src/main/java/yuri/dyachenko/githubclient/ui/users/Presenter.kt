package yuri.dyachenko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.UsersRepo
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val usersRepo: UsersRepo,
    private val router: Router
) : Contract.Presenter() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    override fun onUpdate() = getData()

    override fun onItemClicked(login: String) {
        router.navigateTo(Screens.user(login))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        disposables.add(
            usersRepo
                .getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { viewState.setState(Contract.State.Success(it)) },
                    onError = { viewState.setState(Contract.State.Error) }
                )
        )
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}