package yuri.dyachenko.githubclient.ui.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.ui.base.BasePresenter

class Contract {

    sealed class State {
        data class Success(val list: List<User>) : State()
        data class Error(val e: Throwable) : State()
        object Loading : State()
    }

    interface View : MvpView {

        @AddToEndSingle
        fun setState(state: State)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun onError()
        abstract fun onUpdate()
        abstract fun onItemClicked(login: String, id: Int)
    }
}

