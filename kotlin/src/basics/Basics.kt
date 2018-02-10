/**
 * Teaching myself Kotlin.
 *
 * @author Warwick Hunter
 * @since  2017-12-28
 */
package basics

import java.util.Collections
import java.util.Comparator
import java.util.concurrent.Executors

data class Thing(val name:String)

fun canBeNull(name:String?) : String? {
    return name?.toString()
}

fun main(args : Array<String>) {
    val name = if (!args.isEmpty()) args[0] else "foo"
    println("Hello world $name")

    val things = listOf(Thing("Fred"), Thing("Jacklyn"))
    println("Thing is called $things")
    
    val cbn1:String? = canBeNull("notnull")
    println("Can be null1: $cbn1")

    val cbn2:String? = canBeNull(null)
    println("Can be null2: $cbn2")
    
    objectExpressions()
    singleAbstractMethod()

    // Extension functions on collections
    println(arrayListOf(1, 5, 2).sortedDescending());
    
    operatorOverloading();
    ranges();
    destructuring();
    collections();
}

open class Person(name: String) {
    public open val alias: String = name
    
    override fun toString() : String {
        return "Person[alias=$alias]"
    }
}

fun objectExpressions() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    adHoc.x = 42
    adHoc.y = 43
    println("x=${adHoc.x} y=${adHoc.y}")
    
    val myWife: Person = object : Person("Fiona") {
    }
    val myself: Person = object : Person("Warwick") {
        override val alias = "Wasa"
    }
    println("Myself alias=${myself.alias}, Wife alias=${myWife.alias}")
    
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, object : Comparator<Int> {
        //override fun compare(val1:Int, val2:Int) = val2 - val1
        override fun compare(val1:Int, val2:Int) : Int {
            return val2.compareTo(val1)
        }
    })
    println(arrayList)
}

fun singleAbstractMethod() {
    Executors.newSingleThreadExecutor().execute { println("This runs in a thread pool")}

    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, { x, y -> y - x } )
    println(arrayList)
}

// operator overloading
operator fun Person.inc() : Person {
    return Person(alias + "++")
}

operator fun Person.compareTo(other : Person) : Int {
    return alias.compareTo(other.alias);
}

fun operatorOverloading() {
    var myself = Person("Warwick")
    var mywife = Person("Fiona")
    println(myself)
    ++myself
    println(myself)
    
    println("comparison a > b = ${myself > mywife}")
}

fun ranges() {
    for (i in 1..5) {
        print("$i ")
    }
    println("")
    for (i in 5 downTo 1) {
        print("$i ")
    }
    println("")
    for (i in 0 until 10 step 2) {
        print("$i ")
    }
    println("")
}

data class Name(val first : String, val last : String)

data class Hobby(val name : String, val isFun : Boolean) {
    
    // These two below are needed if this is not declared as a data class
    //operator fun component1() : String = name
    //operator fun component2() : Boolean = isFun
    
    operator fun component3() : String {
        return name + " isLotsOfFun=" + isFun
    }

    override fun toString() : String {
        return "Hobby[name=$name, isFun=$isFun]"
    }
}

// destructuring

data class Result(val httpCode : Int, val httpMessage : String)

fun makeRestRequest() : Result = Result(403, "Badly formed request")
    
fun destructuring() {
    val name = Name("Warwick", "Hunter")
    val(first, last) = name
    println(name)
    println("$first $last")
    
    val hobby = Hobby("Motorcycling", true)
    val(what, isFun, message) = hobby
    println(hobby)
    println("$what $isFun $message")
    
    val names = listOf(Name("Warwick","Hunter"), Name("Jack","ScruffyMutt"))
    for ((f, l) in names) {
        println("name: $f $l")
    }
    
    val (code, reason) = makeRestRequest()
    println("code=$code reason=$reason")
}

fun collections() {
    val names = listOf(Name("Warwick","Hunter"), Name("Jack","ScruffyMutt"), Name("Fiona","Hunter"))
    println(names.map { it.first })
    println(names.filter { it.last == "Hunter" })
    names.forEachIndexed{ index, value ->
        println("position $index contains a $value")
    } 

    val namesSet = names.toSet()
    println(namesSet)
    
    val isHunter : (Name) -> Boolean = {it.last == "Hunter"}
    println("isHunter=" + names.any(isHunter))
    println("allHunter=" + names.all(isHunter))
    println("count Hunter=" + names.count(isHunter))
    println("find Hunter=" + names.find(isHunter))
    
    val result = listOf("abc", "12", "cba123").flatMap { print("$it "); listOf(it.substring(1)) }
    println("")
    println("flatMap " + result)
}