type User5 = { name: string; age: number };
type User5Keys = keyof User5;

let validKeys: User5Keys;
validKeys = "name";
validKeys = "age";

function getProp<T extends object, U extends keyof T>(obj : T, key: U) {
    const val = obj[key];

    if (val === undefined || val === null) {
        throw new Error(`Accessing undefined or null`);
    }

    return val;
}

const user5 = { name: "Max", age: 42 };
const name5 = getProp(user5, "name");
console.log(name5)

const data5 = {id: 1, isStored: true}
const isStored = getProp(data5, "isStored");
console.log(isStored)