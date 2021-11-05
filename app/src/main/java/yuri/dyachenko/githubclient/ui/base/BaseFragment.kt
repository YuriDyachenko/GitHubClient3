package yuri.dyachenko.githubclient.ui.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import yuri.dyachenko.githubclient.R
import yuri.dyachenko.githubclient.showSnackBar

abstract class BaseFragment(
    contentLayoutId: Int,
    private val blockBackPressed: Boolean = false
) : MvpAppCompatFragment(contentLayoutId) {

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
        if (blockBackPressed) {
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(this, backPressedCallback)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun getTextCacheOrWeb(fromCache: Boolean) =
        getString(if (fromCache) R.string.from_cache else R.string.from_web)

    fun showErrorSnackBar(view: View, e: Throwable?, action: (View) -> Unit) {
        view.showSnackBar(
            e?.message ?: getString(R.string.something_broke),
            R.string.reload,
            snackBarCallback,
            action
        )
    }
}