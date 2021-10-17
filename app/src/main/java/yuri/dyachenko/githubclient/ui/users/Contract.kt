package yuri.dyachenko.githubclient.ui.users

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import yuri.dyachenko.githubclient.model.User

class Contract {

    sealed class State {
        data class Success(val list: List<User>) : State()
        data class Error(val e: Throwable) : State()
        object Loading : State()
        data class UserChanged(val user: User) : State()
    }

    interface View : MvpView {

        @AddToEndSingle
        fun setState(state: State)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onError()
        abstract fun onUpdate()
        abstract fun onItemClicked(login: String)
        abstract fun onUpdateUser(user: User)
    }
}

