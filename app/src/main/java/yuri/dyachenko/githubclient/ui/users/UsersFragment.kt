package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUsersBinding
import yuri.dyachenko.githubclient.di.RETROFIT_NAMED
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.model.DataSaveProvider
import yuri.dyachenko.githubclient.network.AndroidNetworkStatusObservable
import yuri.dyachenko.githubclient.ui.base.BaseFragment
import yuri.dyachenko.githubclient.ui.users.Contract.State

class UsersFragment : BaseFragment(R.layout.fragment_users), Contract.View {

    private val binding by viewBinding(FragmentUsersBinding::bind)

    private val router by inject<Router>()
    private val webDataProvider by inject<DataProvider>(named(RETROFIT_NAMED))
    private val roomDataProvider by inject<DataProvider>()
    private val roomDataSaveProvider by inject<DataSaveProvider>()

    private val presenter by moxyPresenter {
        Presenter(
            webDataProvider,
            roomDataProvider,
            roomDataSaveProvider,
            get<AndroidNetworkStatusObservable>(),
            router
        )
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
                usersFromTextView.text =
                    getString(if (state.fromCache) R.string.from_cache else R.string.from_web)
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