package yuri.dyachenko.githubclient.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUserBinding

class UserFragment : MvpAppCompatFragment(), Contract.View {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val presenter by moxyPresenter {
        Presenter(App.usersRepo, userLogin)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setState(state: Contract.State) = with(binding) {
        when (state) {
            is Contract.State.Success -> {
                userLoadingLayout.hide()
                userLoginTextView.text = state.user.login
            }
            Contract.State.Error -> {
                userLoadingLayout.hide()
                userRootView.showSnackBar(
                    R.string.something_broke,
                    R.string.reload
                ) { presenter.onError() }
            }
            Contract.State.Loading -> {
                userLoadingLayout.show()
            }
        }
    }

    companion object {
        private const val ARG_USER_LOGIN = "ARG_USER_LOGIN"

        fun newInstance(login: String): Fragment =
            UserFragment()
                .arguments(ARG_USER_LOGIN to login)
    }
}