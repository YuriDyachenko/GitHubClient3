package yuri.dyachenko.githubclient.ui.repo

import android.view.MenuItem
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentRepoBinding
import yuri.dyachenko.githubclient.di.RETROFIT_NAMED
import yuri.dyachenko.githubclient.model.DataProvider
import yuri.dyachenko.githubclient.network.AndroidNetworkStatusObservable
import yuri.dyachenko.githubclient.ui.base.BaseFragment

class RepoFragment : BaseFragment(R.layout.fragment_repo, true), Contract.View {

    private val binding by viewBinding(FragmentRepoBinding::bind)

    private val webDataProvider by inject<DataProvider>(named(RETROFIT_NAMED))
    private val roomDataProvider by inject<DataProvider>()

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val repoName: String by lazy {
        arguments?.getString(ARG_REPO_NAME).orEmpty()
    }

    private val presenter by moxyPresenter {
        Presenter(
            webDataProvider,
            roomDataProvider,
            get<AndroidNetworkStatusObservable>(),
            userLogin,
            repoName
        )
    }

    override fun setState(state: Contract.State) = with(binding) {
        when (state) {
            is Contract.State.Success -> {
                repoLoadingLayout.hide()
                repoNameTextView.text = state.repo.name
                repoForksCountTextView.text = state.repo.forksCount.toString()
                repoFromTextView.text =
                    getString(if (state.fromCache) R.string.from_cache else R.string.from_web)
            }
            is Contract.State.Error -> {
                repoLoadingLayout.hide()
                repoRootView.showSnackBar(
                    state.e.message ?: getString(R.string.something_broke),
                    R.string.reload,
                    snackBarCallback
                ) { presenter.onError() }
            }
            Contract.State.Loading -> {
                repoLoadingLayout.show()
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
        private const val ARG_REPO_NAME = "ARG_REPO_NAME"

        fun newInstance(userLogin: String, repoName: String): Fragment =
            RepoFragment()
                .arguments(
                    ARG_USER_LOGIN to userLogin,
                    ARG_REPO_NAME to repoName
                )
    }
}