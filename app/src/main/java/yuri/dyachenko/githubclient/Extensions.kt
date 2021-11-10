package yuri.dyachenko.githubclient

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireContext().app

fun ImageView.loadImage(url: String?, placeholderId: Int, errorId: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholderId)
        .error(errorId)
        .into(this)
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.showSnackBar(
    text: String,
    actionTextId: Int,
    callback: Snackbar.Callback,
    action: (View) -> Unit
) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionTextId, action)
        .addCallback(callback)
        .show()
}

fun Fragment.arguments(vararg arguments: Pair<String, Any>): Fragment {
    this.arguments = bundleOf(*arguments)
    return this
}

