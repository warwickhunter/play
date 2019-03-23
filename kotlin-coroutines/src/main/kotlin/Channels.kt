/**
 * Coroutine channels https://kotlinlang.org/docs/reference/coroutines/channels.html
 */
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.time.*

fun channels() {
    example20()
    example21()
    example22()
    example23()
    example24()
    example25()
    example26()
    example27()
    example28()
    example29()
}

fun example20() = runBlocking {
    println("example20")
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) {
            channel.send(x * x)
        }
    }
    // here we print five received integers:
    repeat(5) {
        println(channel.receive())
    }
    println("Done!")
}

fun example21() = runBlocking {
    println("example21")
    val channel = Channel<Instant>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send the time
        for (x in 1..5) {
            channel.send(Instant.now())
        }
        channel.close()
    }
    // here we print received values using `for` loop (until the channel is closed)
    for (y in channel) {
        println("$y received at ${Instant.now()}")
    }
    println("Done!")
}

fun example22() = runBlocking {
    println("example22")
    val times = produceTimes()
    times.consumeEach {
        println("$it received at ${Instant.now()}")
    }
    println("Done!")
}

fun CoroutineScope.produceTimes(): ReceiveChannel<Instant> = produce {
    repeat(5) {
        send(Instant.now())
    }
}

fun example23() = runBlocking {
    println("example23")
    val numbers = produceNumbers() // produces integers from 1 and on
    val squares = square(numbers) // squares integers
    repeat(5) {
        // print first five
        println(squares.receive())
    }
    repeat(5) {
        println(numbers.receive())
    }
    repeat(5) {
        println(squares.receive())
    }
    println("Done!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines
}

fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}

fun example24() = runBlocking {
    println("example24")
    var cur = numbersFrom(2)
    for (i in 1..10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}

// fan out
fun example25() = runBlocking {
    println("example25")
    val producer = produceNumbers2()
    repeat(5) {
        launchProcessor(it, producer)
    }
    delay(950)
    producer.cancel() // cancel producer coroutine and thus kill them all
}

fun CoroutineScope.produceNumbers2() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

// fan in
fun example26() = runBlocking {
    println("example26")
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(6) { // receive first six
        println(channel.receive())
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}

// buffered channels
fun example27() = runBlocking {
    println("example27")
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch { // launch sender coroutine
        repeat(10) {
            println("Sending $it") // print before sending each element
            channel.send(it) // will suspend when buffer is full
        }
    }
    delay(1000)
    repeat(10) {
        println("Received ${channel.receive()}")
    }
    sender.cancel() // cancel sender coroutine
}

// fairness of channels
fun example28() = runBlocking {
    println("example28")
    val table = Channel<Ball>() // a shared table
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0)) // serve the ball
    delay(1000) // delay 1 second
    coroutineContext.cancelChildren() // game over, cancel them
}

data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // receive the ball in a loop
        ball.hits++
        println("$name $ball")
        delay(300) // wait a bit
        table.send(ball) // send the ball back
    }
}

// ticker channel
fun example29() = runBlocking {
    println("example29")
    val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0) // create ticker channel
    var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Initial element is available immediately: $nextElement") // initial delay hasn't passed yet

    nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements has 100ms delay
    println("Next element is not ready in 50 ms: $nextElement")

    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 100 ms: $nextElement")

    // Emulate large consumption delays
    println("Consumer pauses for 150ms")
    delay(150)
    // Next element is available immediately
    nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Next element is available immediately after large consumer delay: $nextElement")
    // Note that the pause between `receive` calls is taken into account and next element arrives faster
    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

    tickerChannel.cancel() // indicate that no more elements are needed
}
