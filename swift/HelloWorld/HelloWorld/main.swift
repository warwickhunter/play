//
//  main.swift
//  HelloWorld
//
//  Created by Warwick Hunter on 2019-09-15.
//  Copyright © 2019 Warwick Hunter. All rights reserved.
//
// https://docs.swift.org/swift-book/GuidedTour/GuidedTour.html
//

//import Foundation

print("Hello, World!")

let label = "The width is"
let width: Float = 94.0
let widthLabel = "\(label) \(width)"
print(widthLabel)

let apples = 42
let oranges = 7
let quotation = """
I said "I have \(apples) apples."
And then I said "I have \(apples + oranges) pieces of fruit."
"""
print(quotation)

var shoppingList = ["catfish", "water", "tulips"]
shoppingList[1] = "bottle of water"
shoppingList.append("trim milk")
print(shoppingList)
shoppingList = [] // type is inferred
var shoppingList2 = [String]()

var occupations = [
    "Malcolm": "Captain",
    "Kaylee": "Mechanic",
]
occupations["Jayne"] = "Public Relations"
print(occupations)
occupations = [:]

let individualScores = [75, 43, 103, 87, 12]
var teamScore = 0
for score in individualScores {
    if score > 50 {
        teamScore += 3
    } else {
        teamScore += 1
    }
}
print(teamScore)

var optionalString: String? = "Hello"
print(optionalString == nil)
// Prints "false"

var optionalName: String? = nil // "John Appleseed"
var greeting = "Hello!"
if let name = optionalName {
    greeting = "Hello, \(name)"
} else {
    greeting = "Bonsoir"
}
print(greeting)

let nickName: String? = nil
let fullName: String = "John Appleseed"
let informalGreeting = "Hi \(nickName ?? fullName)"
print(informalGreeting)

let vegetable = "red pepper"
switch vegetable {
    case "celery":
        print("Add some raisins and make ants on a log.")
    case "cucumber", "watercress":
        print("That would make a good tea sandwich.")
    case let x where x.hasSuffix("pepper"):
        print("Is it a spicy \(x)?")
    default:
        print("Everything tastes good in soup.")
}

let interestingNumbers = [
    "Prime": [2, 3, 5, 7, 11, 13],
    "Fibonacci": [1, 1, 2, 3, 5, 8],
    "Square": [1, 4, 9, 16, 25],
]
var largest = 0
var largestKind: String? = nil
for (kind, numbers) in interestingNumbers {
    for number in numbers {
        if number > largest {
            largest = number
            largestKind = kind
        }
    }
}
print("\(largest) is the largest and of kind \(largestKind ?? "none")")
for key in interestingNumbers.keys {
    print(key)
}

var n = 2
while n < 100 {
    n *= 2
}
print(n)

var m = 2
repeat {
    m *= 2
} while m < 100
print(m)

var total = 0
for i in 0..<4 {
    total += i
}
print(total)
total = 0
for i in 0...4 {
    total += i
}
print(total)

func greet(person: String, day: String) -> String {
    return "Hello \(person), today is \(day)."
}
print(greet(person: "Bob", day: "Tuesday"))

func greet1(_ person: String, on day: String) -> String {
    return "Hello \(person), today is \(day)."
}
print(greet1("John", on: "Friday"))

func calculateStatistics(scores: [Int]) -> (min: Int, max: Int, sum: Int) {
    var min = scores[0]
    var max = scores[0]
    var sum = 0
    
    for score in scores {
        if score > max {
            max = score
        } else if score < min {
            min = score
        }
        sum += score
    }
    
    return (min, max, sum)
}
let statistics = calculateStatistics(scores: [5, 3, 100, 3, 9])
print(statistics.sum)
print(statistics.0)
print(statistics.2)
print(statistics)

func makeIncrementer() -> ((Int) -> Int) {
    func addOne(number: Int) -> Int {
        return 1 + number
    }
    return addOne
}
var increment = makeIncrementer()
print(increment(7))
