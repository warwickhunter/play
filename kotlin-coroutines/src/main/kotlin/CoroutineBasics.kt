/**
 * Coroutines basics from https://kotlinlang.org/docs/reference/coroutines/basics.html
 */
import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun basics() {
    example1()
    example2()
    example3()
    example4()
    example5()
    example6()
    example7()
    example8()
    example9()
    example10()
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

fun example6() = runBlocking { // this: CoroutineScope
    println("example6")
    launch { // launch new coroutine in the scope of runBlocking
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

fun example7() = runBlocking { // this: CoroutineScope
    println("example7")
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // Creates a new coroutine scope
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // This line will be printed before nested launch
    }

    println("Coroutine scope is over") // This line is not printed until nested launch completes
}

fun example8() = runBlocking {
    println("example8")
    launch { doWorld() }
    println("Hello,")
}

// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

fun example9() {
    println("example9")
    runBlocking {
        repeat(100_000) {
            // launch a lot of coroutines
            launch {
                delay(1000L)
                print(".")
            }
        }
    }
    println("")
}

fun example10() = runBlocking {
    println("example10")
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
}
