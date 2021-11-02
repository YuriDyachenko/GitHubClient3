package yuri.dyachenko.githubclient.ui.user

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUserBinding
import yuri.dyachenko.githubclient.network.AndroidNetworkStatusObservable
import yuri.dyachenko.githubclient.ui.base.BaseFragment

class UserFragment : BaseFragment(R.layout.fragment_user, true), Contract.View {

    private val binding by viewBinding(FragmentUserBinding::bind)

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val presenter by moxyPresenter {
        Presenter(
            app.dataProvider,
            app.roomDataProvider,
            app.roomDataSaveProvider,
            AndroidNetworkStatusObservable(app),
            app.router,
            userLogin
        )
    }

    private val adapter by lazy { Adapter(presenter, userLogin) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.reposRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@UserFragment.adapter
        }
    }

    override fun setState(state: Contract.State) = with(binding) {
        when (state) {
            is Contract.State.Success -> {
                userLoadingLayout.hide()
                userLoginTextView.text = userLogin
                adapter.submitList(state.list)
                userFromTextView.text =
                    getString(if (state.fromCache) R.string.from_cache else R.string.from_web)
            }
            is Contract.State.Error -> {
                userLoadingLayout.hide()
                userRootView.showSnackBar(
                    state.e.message ?: getString(R.string.something_broke),
                    R.string.reload,
                    snackBarCallback
                ) { presenter.onError() }
            }
            Contract.State.Loading -> {
                userLoadingLayout.show()
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
        private const val ARG_USER_LOGIN = "ARG_USER_LOGIN"

        fun newInstance(login: String): Fragment =
            UserFragment()
                .arguments(ARG_USER_LOGIN to login)
    }
}