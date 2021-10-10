package yuri.dyachenko.githubclient.ui.users

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.apply {
            usersRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            usersRecyclerView.adapter = adapter
        }
    }.root

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setState(state: State) = with(binding) {
        when (state) {
            is State.Success -> {
                usersLoadingLayout.hide()
                adapter.submitList(state.list)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_update -> {
            presenter.onUpdate()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}