// const person: {
//     name: string;
//     age: number;
//     hobbies: string[];
//     role: [number, string];
// }  = {
//     name: "Warwick",
//     age: 58,
//     hobbies: ["Sport", "Cooking"],
//     role: [2, "author"]
// };

enum Role { ADMIN, READ_ONLY, AUTHOR };

const person = {
    name: "Warwick",
    age: 58,
    hobbies: ["Sport", "Cooking"],
    role: Role.ADMIN
};

let favouriteActivities: string[];
favouriteActivities = ["foo"];

console.log(person);

for (const hobby of person.hobbies) {
    console.log(hobby.toUpperCase());
}

if (person.role === Role.ADMIN) {
    console.log("is admin");
}