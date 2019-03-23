package coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    example1()
    example2()
    example3()
    example4()
    example5()
}

fun example1() {
    println("example1")
    GlobalScope.launch {
        // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

fun example2() {
    println("example2")
    GlobalScope.launch {
        // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    runBlocking {
        delay(2000L)
    }
}

fun example3() {
    println("example3")
    thread(start = true) {
        // launch new coroutine in background and continue
        Thread.sleep(1000L) // block main thread for 2 seconds to keep JVM alive
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

fun example4() = runBlocking<Unit> {
    println("example4")
    GlobalScope.launch {
        // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
delay(2000L)
}

fun example5() = runBlocking<Unit> {
    println("example5")
    val job = GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join()
}