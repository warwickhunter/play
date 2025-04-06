console.log('Your code goes here, or here…');

let userName: string;
userName = "Jack";
console.log(typeof userName);

let userAge = 38;
console.log(typeof userAge);

let foo;
console.log(typeof foo);

function add(n1: number, n2 = 0) {
    const result = n1 + n2;
    console.log("Result is " + result);
    return result;
}

const number1 = 5;
const number2 = 2.8;

add(number1, number2);
add(number1);

let container: string | number = 42;
console.log(typeof container);
container = "foo";
console.log(typeof container);

let hobbies = ["Rugby", "Motorcycles"];
hobbies.push("Cooking");
console.log(hobbies);

// let users: (string | number)[] = [];
let users: Array<string | number> = [];
users.push("Bob");
users.push(42);
console.log(users);

let possibleResults: [number, number]; // tuple
possibleResults = [1, -1];
// possibleResults = [0, 1, 2]; // compile error

let user: { // Object type
    name: string;
    age: number;
} = {
    name: "Ted",
    age: 42
}
console.log(user);

let val: {} = "foo";
console.log(typeof val);

let data: Record<string, number | string>;
data = {
    entry1: 1,
    entry2: "something"
};
console.log(typeof data);
console.log(data);
