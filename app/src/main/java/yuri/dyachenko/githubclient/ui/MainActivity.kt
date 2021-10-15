package yuri.dyachenko.githubclient.ui

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import yuri.dyachenko.githubclient.app

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)

    override fun onResumeFragments() {
        super.onResumeFragments()
        app.navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: app.router.newRootScreen(Screens.users())
    }

    override fun onPause() {
        app.navigatorHolder.removeNavigator()
        super.onPause()
    }
}