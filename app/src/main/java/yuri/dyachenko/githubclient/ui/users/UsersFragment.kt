package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUsersBinding
import yuri.dyachenko.githubclient.ui.base.BaseFragment
import yuri.dyachenko.githubclient.ui.users.Contract.State
import javax.inject.Inject
import javax.inject.Provider

class UsersFragment : BaseFragment(R.layout.fragment_users), Contract.View {

    private val binding by viewBinding(FragmentUsersBinding::bind)

    @InjectPresenter
    lateinit var presenter: Presenter

    @Inject
    lateinit var presenterProvider: Provider<Presenter>

    @ProvidePresenter
    fun providePresenter(): Presenter = presenterProvider.get()

    private val adapter by lazy { Adapter(presenter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        app.dagger.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.usersRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@UsersFragment.adapter
        }
    }

    override fun setState(state: State) {
        when (state) {
            is State.Success -> setState(state)
            is State.Error -> setState(state)
            is State.Loading -> setState()
        }
    }

    private fun setState(state: State.Success) = with(binding) {
        usersLoadingLayout.hide()
        adapter.submitList(state.list)
        usersFromTextView.text = getTextCacheOrWeb(state.fromCache)
    }

    private fun setState(state: State.Error) = with(binding) {
        usersLoadingLayout.hide()
        showErrorSnackBar(usersRootView, state.e) { presenter.onError() }
    }

    private fun setState() = with(binding) {
        usersLoadingLayout.show()
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