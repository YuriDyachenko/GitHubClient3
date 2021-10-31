package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUsersBinding
import yuri.dyachenko.githubclient.ui.users.Contract.State

class UsersFragment : MvpAppCompatFragment(R.layout.fragment_users), Contract.View {

    private val binding by viewBinding(FragmentUsersBinding::bind)

    private val presenter by moxyPresenter {
        Presenter(app.dataProvider, app.router)
    }

    private val adapter by lazy { Adapter(presenter) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.usersRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@UsersFragment.adapter
        }
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
            presenter.onUpdate()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}