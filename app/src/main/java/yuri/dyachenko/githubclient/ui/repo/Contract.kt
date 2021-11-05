package yuri.dyachenko.githubclient.ui.repo

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip
import yuri.dyachenko.githubclient.model.Repo
import yuri.dyachenko.githubclient.ui.base.BasePresenter

class Contract {

    sealed class State {
        data class Success(val repo: Repo, val fromCache: Boolean) : State()
        data class Error(val e: Throwable) : State()
        object Loading : State()
    }

    interface View : MvpView {

        @AddToEndSingle
        fun setState(state: State)

        @Skip
        fun getData()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun onDataReady(userLogin: String, repoName: String)
    }
}