package yuri.dyachenko.githubclient.ui.user

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUserBinding
import yuri.dyachenko.githubclient.ui.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class UserFragment : BaseFragment(R.layout.fragment_user, true), Contract.View {

    private val binding by viewBinding(FragmentUserBinding::bind)

    @InjectPresenter
    lateinit var presenter: Presenter

    @Inject
    lateinit var presenterProvider: Provider<Presenter>

    @ProvidePresenter
    fun providePresenter(): Presenter = presenterProvider.get()

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val adapter by lazy { Adapter(presenter, userLogin) }

    override fun getData() {
        presenter.onDataReady(userLogin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        app.dagger.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.reposRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@UserFragment.adapter
        }
    }

    override fun setState(state: Contract.State) {
        when (state) {
            is Contract.State.Success -> setState(state)
            is Contract.State.Error -> setState(state)
            is Contract.State.Loading -> setState()
        }
    }

    private fun setState(state: Contract.State.Success) = with(binding) {
        userLoadingLayout.hide()
        userLoginTextView.text = userLogin
        adapter.submitList(state.list)
        userFromTextView.text = getTextCacheOrWeb(state.fromCache)
    }

    private fun setState(state: Contract.State.Error) = with(binding) {
        userLoadingLayout.hide()
        showErrorSnackBar(userRootView, state.e) { presenter.onError() }
    }

    private fun setState() = with(binding) {
        userLoadingLayout.show()
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