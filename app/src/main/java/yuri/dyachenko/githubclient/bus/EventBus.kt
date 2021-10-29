package yuri.dyachenko.githubclient.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EventBus {

    private val bus = PublishSubject.create<Event>()

    fun post(event: Event) = bus.onNext(event)

    fun get(): Observable<Event> = bus
}