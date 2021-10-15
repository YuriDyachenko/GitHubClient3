package yuri.dyachenko.githubclient.ui.user

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import yuri.dyachenko.githubclient.*
import yuri.dyachenko.githubclient.databinding.FragmentUserBinding

class UserFragment : MvpAppCompatFragment(R.layout.fragment_user), Contract.View {

    private val binding by viewBinding(FragmentUserBinding::bind)

    private val userLogin: String by lazy {
        arguments?.getString(ARG_USER_LOGIN).orEmpty()
    }

    private val presenter by moxyPresenter {
        Presenter(App.usersRepo, userLogin)
    }

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
        }
    }

    private val snackBarCallback = object : Snackbar.Callback() {

        override fun onShown(sb: Snackbar?) {
            super.onShown(sb)
            backPressedCallback.isEnabled = true
        }

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            backPressedCallback.isEnabled = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun setState(state: Contract.State) = with(binding) {
        when (state) {
            is Contract.State.Success -> {
                userLoadingLayout.hide()
                userLoginTextView.text = state.user.login
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

        fun newInstance(login: String): Fragment =
            UserFragment()
                .arguments(ARG_USER_LOGIN to login)
    }
}