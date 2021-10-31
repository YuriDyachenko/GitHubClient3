package yuri.dyachenko.githubclient.ui.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment

abstract class BlockingBackFragment(
    contentLayoutId: Int
) : MvpAppCompatFragment(contentLayoutId) {

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
        }
    }

    protected val snackBarCallback = object : Snackbar.Callback() {

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

}