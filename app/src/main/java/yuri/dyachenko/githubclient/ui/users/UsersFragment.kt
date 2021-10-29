package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.bus.Event
import yuri.dyachenko.githubclient.databinding.FragmentUsersBinding
import yuri.dyachenko.githubclient.model.User
import yuri.dyachenko.githubclient.ui.users.Contract.State
import java.util.concurrent.TimeUnit

class UsersFragment : MvpAppCompatFragment(R.layout.fragment_users), Contract.View {

    private val binding by viewBinding(FragmentUsersBinding::bind)

    private val presenter by moxyPresenter {
        Presenter(app.usersRepo, app.router)
    }

    private val adapter by lazy { Adapter(presenter) }

    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.usersRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@UsersFragment.adapter
        }
        subscribeOnEvents()
        subscribeOnTimer()
    }

    private fun subscribeOnTimer() {
        disposables.add(
            Observable
                .interval(TIMER_INITIAL_DELAY, TIMER_PERIOD, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { v ->
                    adapter.getRandomUser()?.let {
                        if (v.even()) {
                            increaseUserLikes(it)
                        } else {
                            increaseUserDislikes(it)
                        }
                    }
                }
        )
    }

    private fun increaseUserLikes(user: User) {
        disposables.add(
            app.usersRepo
                .likeUserByLogin(user.login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { app.bus.post(Event.UserUpdate(it)) }
        )
    }

    private fun increaseUserDislikes(user: User) {
        disposables.add(
            app.usersRepo
                .dislikeUserByLogin(user.login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { app.bus.post(Event.UserUpdate(it)) }
        )
    }

    private fun subscribeOnEvents() {
        disposables.add(
            app.bus.get().subscribe { event ->
                when (event) {
                    is Event.UserUpdate -> {
                        presenter.onUpdateUser(event.user)
                    }
                    Event.UsersUpdate -> {
                        presenter.onUpdate()
                    }
                }
            }
        )
    }

    override fun setState(state: State) = with(binding) {
        when (state) {
            is State.Success -> {
                usersLoadingLayout.hide()
                adapter.submitList(state.list)
            }
            is State.Error -> {
                usersLoadingLayout.hide()
                usersRootView.showSnackBar(
                    state.e.message ?: getString(R.string.something_broke),
                    R.string.reload
                ) { presenter.onError() }
            }
            is State.UserChanged -> {
                usersLoadingLayout.hide()
                adapter.submitUser(state.user)
            }
            State.Loading -> {
                usersLoadingLayout.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_update_menu_item -> {
            app.bus.post(Event.UsersUpdate)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    companion object {
        const val TIMER_INITIAL_DELAY = 3L
        const val TIMER_PERIOD = 2L

        fun newInstance() = UsersFragment()
    }
}