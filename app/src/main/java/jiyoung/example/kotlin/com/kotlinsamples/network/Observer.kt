package jiyoung.example.kotlin.com.kotlinsamples.network

/**
 * Created by user on 2018-05-23.
 */
interface Observer {
    abstract fun update(networkChecker: NetworkChecker)
}