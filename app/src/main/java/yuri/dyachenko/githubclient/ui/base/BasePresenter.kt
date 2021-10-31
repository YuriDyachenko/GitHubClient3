package yuri.dyachenko.githubclient.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun Disposable.autoDispose() {
        disposables.add(this)
    }
}