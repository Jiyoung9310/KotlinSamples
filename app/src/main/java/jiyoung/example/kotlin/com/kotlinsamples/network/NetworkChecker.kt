package jiyoung.example.kotlin.com.kotlinsamples.network

/**
 * Created by user on 2018-05-23.
 */
abstract class NetworkChecker{
    private val observers = arrayListOf<Observer>()

    fun addObserver (observer: Observer) {
        observers.add(observer)
    }

    fun deleteObserver (observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        val it = observers.iterator()
        while (it.hasNext()) {
            val o: Observer = it.next()
            o.update(this)
        }
    }

    abstract fun getStatus(): Boolean

    abstract fun execute()
}