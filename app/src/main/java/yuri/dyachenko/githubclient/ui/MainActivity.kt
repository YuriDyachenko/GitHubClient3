package yuri.dyachenko.githubclient.ui

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import yuri.dyachenko.githubclient.App

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: App.router.newRootScreen(Screens.users())
    }

    override fun onPause() {
        App.navigatorHolder.removeNavigator()
        super.onPause()
    }
}