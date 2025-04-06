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

enum Role {
    ADMIN,
    READ_ONLY,
    AUTHOR
};
let userRole: Role = Role.ADMIN;
console.log(userRole)

let userRole2: "admin" | "editor" | "guest" = "admin"; // Literal types
console.log(userRole2)
let possibleResults2: [1 | -1, 1 | -1];
possibleResults2 = [1, 1];
console.log(possibleResults2)

type Role3 = "admin" | "editor" | "guest"; // type alias or custom type
let userRole3: Role3 = "admin";
function access(role: Role3) {
    // …
}
console.log(userRole3)

function add2(n1: number, n2: number): number { // function return type
    return n1 + n2;
}

function log(message: string) : void { // void type
    console.log(message);
}

function logAndThrow(errorMessage: string): never { // never type
    console.log(errorMessage);
    throw new Error(errorMessage);
}

const logMsg = (msg: string): void => { // functions as types
    console.log(msg);
}
function performJob(callback : (m: string) => void) {
    callback("hello");
}
performJob(logMsg);
performJob(log);
type User = {
    name: string;
    age: number;
    greet: () => string;
};
let user2: User = {
    name: "Warwick",
    age: 58,
    greet() {
        console.log("hello " + this.name);
        return this.name;
    }
}
user2.greet();

let a: null | string; // null and undefined
a = null;
a = "foo";
let b: undefined | string;
b = undefined;
b = "bar";

// const inputElement = document.getElementById("user-name"); // type narrowing
// if (!inputElement) {
//     throw Error("Element not found");
// }
// console.log(inputElement.innerText);

// const inputElement = document.getElementById("user-name")!; // forced not null
// console.log(inputElement.innerText);
// const inputElement = document.getElementById("user-name");
// console.log(inputElement!.innerText);

// const inputElement = document.getElementById("user-name"); // optional chaining
// console.log(inputElement?.innerText);

// const inputElement = document.getElementById("user-name") as HTMLInputElement | null; // type casting
// console.log(inputElement?.value);

function process2(val: unknown) { // unknown type
    if (typeof val === "object" && !!val && "log" in val && typeof val.log === "function") {
        val.log();
    }
}

function generateError(msg?: string) { // optional values
    throw new Error(msg);
}
generateError();
type User2 = {
    name: string;
    age: number;
    hobbies: string[];
    role?: "admin" | "guest"
};

let input = null; // nullish coalescing
const didProvideInput = input ?? false;
