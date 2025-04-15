// Index type
type DataStore = {
    [prop: string]: number | boolean;
}
let store : DataStore = {};
store.id = 5;
store.isOpen = false;
console.log(store);

// Revisiting Record
let someObj: Record<string, number | boolean>; // same as above but using Record
someObj = {};
someObj.id = 42;
someObj.isOpen = true;
console.log(someObj);

// as const
let roles = ['admin', 'guest', 'editor'] as const;
// roles.push('foo'); error!
const firstRole = roles[0];
console.log(firstRole);

// satisfies
const dataEntries = {
    entry1: 0.51,
    entry2: -1.23
} satisfies Record<string, number>;
console.log(dataEntries.entry1, dataEntries.entry2);
// dataEntries.entry3 error!

