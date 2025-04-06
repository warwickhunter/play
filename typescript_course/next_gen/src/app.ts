const add = (a: number, b: number = 1) => a + b;

console.log(add(1, 2));

const printOutput: (a: number | string) => void = output => console.log(output);

printOutput(add(5, 2));
printOutput(add(5));

// spread operator
const hobbies = ['Sports', 'Music', 'Reading'];
const activeHobbies = ["Hiking", ...hobbies];
//activeHobbies.push(...hobbies);
console.log(hobbies);
console.log(activeHobbies);

const person = {
    name: 'John',
    age: 30,
};
const copiedPerson = { ...person };
console.log(copiedPerson);

// rest parameters
const add2 = (...numbers: number[]) => {
    return numbers.reduce((curResult, curValue) => {
        return curResult + curValue;
    }, 0);
}
console.log(add2(1, 2, 3, 4, 5));

// destructuring
const [hobby1, hobby2, ...remainingHobbies] = hobbies;
console.log(hobby1, hobby2, remainingHobbies);
const { name: userName, age } = person;
console.log(userName, age);
