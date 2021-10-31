package yuri.dyachenko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.ui.Screens

class Presenter(
    private val dataProvider: DataProvider,
    private val router: Router
) : Contract.Presenter() {

    override fun onFirstViewAttach() = getData()

    override fun onError() = getData()

    override fun onUpdate() = getData()

    override fun onItemClicked(userLogin: String) {
        router.navigateTo(Screens.user(userLogin))
    }

    private fun getData() {
        viewState.setState(Contract.State.Loading)

        dataProvider
            .getUsers()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { viewState.setState(Contract.State.Success(it)) },
                onError = { viewState.setState(Contract.State.Error(it)) }
            ).autoDispose()
    }
}