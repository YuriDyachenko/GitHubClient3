package yuri.dyachenko.githubclient.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUserBinding
import yuri.dyachenko.githubclient.ui.base.BlockingBackFragment

class UserFragment : BlockingBackFragment(R.layout.fragment_user), Contract.View {

    private val binding by viewBinding(FragmentUserBinding::bind)

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val userId: Int by lazy {
        arguments?.getInt(ARG_USER_ID) ?: NO_USER_ID
    }

    private val presenter by moxyPresenter {
        Presenter(app.dataProvider, app.router, userId)
    }

    private val adapter by lazy { Adapter(presenter) }

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

    companion object {
        private const val ARG_USER_LOGIN = "ARG_USER_LOGIN"
        private const val ARG_USER_ID = "ARG_USER_ID"
        private const val NO_USER_ID = 0

        fun newInstance(login: String, id: Int): Fragment =
            UserFragment()
                .arguments(
                    ARG_USER_LOGIN to login,
                    ARG_USER_ID to id
                )
    }
}