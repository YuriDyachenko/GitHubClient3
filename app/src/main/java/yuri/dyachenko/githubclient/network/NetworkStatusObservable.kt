package yuri.dyachenko.githubclient.network

import io.reactivex.Observable

interface NetworkStatusObservable {

    fun isOnline(): Observable<Boolean>
}