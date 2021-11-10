package yuri.dyachenko.githubclient.ui

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import yuri.dyachenko.githubclient.app
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: Screens

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.dagger.inject(this)
        savedInstanceState ?: router.newRootScreen(screens.users())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}