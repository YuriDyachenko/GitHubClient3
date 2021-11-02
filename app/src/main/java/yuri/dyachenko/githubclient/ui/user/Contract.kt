package yuri.dyachenko.githubclient.ui.user

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.ui.base.BasePresenter

class Contract {

    sealed class State {
        data class Success(val list: List<Repo>, val fromCache: Boolean) : State()
        data class Error(val e: Throwable) : State()
        object Loading : State()
    }

    interface View : MvpView {

        @AddToEndSingle
        fun setState(state: State)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun onItemClicked(userLogin: String, repoName: String)
    }
}