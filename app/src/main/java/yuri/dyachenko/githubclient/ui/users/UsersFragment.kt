package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUsersBinding
import yuri.dyachenko.githubclient.ui.users.Contract.State

class UsersFragment : MvpAppCompatFragment(), Contract.View {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter {
        Presenter(App.usersRepo, App.router)
    }

    private val adapter by lazy { Adapter(presenter) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        _binding = it
        binding.usersRecyclerView.adapter = adapter
    }.root

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setState(state: State) = with(binding) {
        when (state) {
            is State.Success -> {
                usersLoadingLayout.hide()
                adapter.setUsers(state.list)
            }
            State.Error -> {
                usersLoadingLayout.hide()
                usersRootView.showSnackBar(
                    R.string.something_broke,
                    R.string.reload
                ) { presenter.onError() }
            }
            State.Loading -> {
                usersLoadingLayout.show()
            }
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}