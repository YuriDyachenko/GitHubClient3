package yuri.dyachenko.githubclient.ui.repo

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentRepoBinding
import yuri.dyachenko.githubclient.ui.base.BlockingBackFragment

class RepoFragment : BlockingBackFragment(R.layout.fragment_repo), Contract.View {

    private val binding by viewBinding(FragmentRepoBinding::bind)

    private val repoId: Int by lazy {
        arguments?.getInt(ARG_REPO_ID) ?: NO_REPO_ID
    }

    private val presenter by moxyPresenter {
        Presenter(app.dataProvider, repoId)
    }

    override fun setState(state: Contract.State) = with(binding) {
        when (state) {
            is Contract.State.Success -> {
                repoLoadingLayout.hide()
                repoNameTextView.text = state.repo.name
                repoForksCountTextView.text = state.repo.forksCount.toString()
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

    companion object {
        private const val ARG_REPO_ID = "ARG_REPO_ID"
        private const val NO_REPO_ID = 0

        fun newInstance(repoId: Int): Fragment =
            RepoFragment()
                .arguments(ARG_REPO_ID to repoId)
    }
}