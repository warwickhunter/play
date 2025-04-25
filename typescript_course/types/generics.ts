// generic types
let names = Array<string>("Max", "Anna");
let names1 : Array<string> = ["Max", "Anna"];
console.log(names, names1);

type DataStore<T> = {
    [key: string]: T;
};

let store: DataStore<string | boolean> = {};
store.name = "Max";
store.isInstructor = true;

// generic function
function merge<T>(a: T, b : T) {
    return [a, b];
}
const ids = merge(1, 2);
console.log(ids);

// generic constraints
function mergeObj<T extends object, U extends object>(a : T, b : U) {
    return { ...a, ...b };
}
const merged = mergeObj({ userName: "Max"}, { age: 42 });
console.log(merged);

// generic classes
class User<T> {
    constructor(public id: T) {}
}
const user = new User("42");
console.log(user);