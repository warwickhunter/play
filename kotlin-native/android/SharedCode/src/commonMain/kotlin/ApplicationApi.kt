package org.kotlin.mpp.mobile

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.Url
import io.ktor.http.takeFrom
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal expect val ApplicationDispatcher: CoroutineDispatcher

/**
 * A facade over the Ktor HTTP client that fetches some data from the internet.
 *
 * @author Warwick Hunter
 * @since 2019-05-05
 */
class ApplicationApi {

    private val client = HttpClient()

    private val address = Url("https://tools.ietf.org/rfc/rfc1866.txt")

    fun fetch(callback: (String) -> Unit) {
        GlobalScope.apply {
            launch(ApplicationDispatcher) {
                val result: String = client.get {
                    url.takeFrom(address).build()
                }
                callback(result)
            }
        }
    }

}

