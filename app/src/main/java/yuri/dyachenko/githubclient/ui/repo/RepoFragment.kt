package yuri.dyachenko.githubclient.ui.repo

import android.view.MenuItem
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.inject
import yuri.dyachenko.githubclient.R
import yuri.dyachenko.githubclient.arguments
import yuri.dyachenko.githubclient.databinding.FragmentRepoBinding
import yuri.dyachenko.githubclient.hide
import yuri.dyachenko.githubclient.show
import yuri.dyachenko.githubclient.ui.base.BaseFragment

class RepoFragment : BaseFragment(R.layout.fragment_repo, true), Contract.View {

    private val binding by viewBinding(FragmentRepoBinding::bind)

    @InjectPresenter
    lateinit var presenter: Presenter

    private val presenterProvider by inject<Presenter>()

    @ProvidePresenter
    fun providePresenter() = presenterProvider

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val repoName: String by lazy {
        arguments?.getString(ARG_REPO_NAME).orEmpty()
    }

    override fun getData() {
        presenter.onDataReady(userLogin, repoName)
    }

    override fun setState(state: Contract.State) {
        when (state) {
            is Contract.State.Success -> setState(state)
            is Contract.State.Error -> setState(state)
            is Contract.State.Loading -> setState()
        }
    }

    private fun setState(state: Contract.State.Success) = with(binding) {
        repoLoadingLayout.hide()
        repoNameTextView.text = state.repo.name
        repoForksCountTextView.text = state.repo.forksCount.toString()
        repoFromTextView.text = getTextCacheOrWeb(state.fromCache)
    }

    private fun setState(state: Contract.State.Error) = with(binding) {
        repoLoadingLayout.hide()
        showErrorSnackBar(repoRootView, state.e) { presenter.onError() }
    }

    private fun setState() = with(binding) {
        repoLoadingLayout.show()
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
        private const val ARG_REPO_NAME = "ARG_REPO_NAME"

        fun newInstance(userLogin: String, repoName: String): Fragment =
            RepoFragment()
                .arguments(
                    ARG_USER_LOGIN to userLogin,
                    ARG_REPO_NAME to repoName
                )
    }
}