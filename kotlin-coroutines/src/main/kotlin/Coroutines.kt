/**
 * Main entry point just to make it easy to invoke one part of the examples
 */
fun main(args: Array<String>) {

    val flags = if (args.size > 0) args[0] else "bch"

    val lookup = mapOf<String, () -> Unit> (
        "b" to ::basics,
        "c" to ::cancellations,
        "h" to ::channels
    )

    for ((flag, method) in lookup) {
        if (flags.contains(flag)) {
            method.invoke()
        }
    }
}