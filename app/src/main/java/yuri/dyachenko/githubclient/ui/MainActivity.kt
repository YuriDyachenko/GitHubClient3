package yuri.dyachenko.githubclient.ui

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)

    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: router.newRootScreen(get<Screens>().users())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}