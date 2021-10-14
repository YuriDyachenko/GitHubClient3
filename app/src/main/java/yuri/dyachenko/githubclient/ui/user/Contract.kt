package yuri.dyachenko.githubclient.ui.user

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import yuri.dyachenko.githubclient.model.User

class Contract {

    sealed class State {
        data class Success(val user: User) : State()
        data class Error(val e: Throwable) : State()
        object Loading : State()
    }

    interface View : MvpView {

        @AddToEndSingle
        fun setState(state: State)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onError()
    }
}