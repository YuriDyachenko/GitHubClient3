package yuri.dyachenko.githubclient.ui

import com.github.terrakok.cicerone.Screen

interface Screens {

    fun users(): Screen
    fun user(userLogin: String): Screen
    fun repo(userLogin: String, repoName: String): Screen
}