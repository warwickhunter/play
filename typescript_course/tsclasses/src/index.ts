// classes
class User {
    name: string;
    age: number;

    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }
}
console.log(new User('John', 42));

// property shortcut
class User2 {
    constructor(public name: string, public age: number) {}
}
const ted = new User2('Ted', 43);
console.log(ted);

// visibility
class User3 {
    public hobbies: string[] = [];
    private hidden: string = 'hidden';

    constructor(public name: string, public age: number) {}
}
let max = new User3('Max', 21);
console.log(max);

// readonly
class User4 {
    public readonly hobbies: string[] = [];

    constructor(public name: string, public age: number) {}
}
let phil = new User4('Phil', 22);
phil.hobbies.push('coding');
console.log(phil);

// getters
class User5 {
    constructor(private firstName: string, private lastName: string) {}

    get fullName() {
        return this.firstName + ' ' + this.lastName
    }
}
let jen = new User5('Jen', 'Jones');
console.log(jen.fullName);

// setters
class User6 {
    private _firstName: string = '';
    private _lastName: string = '';

    get fullName() {
        return this._firstName + ' ' + this._lastName
    }

    set firstName(value: string) {
        if (value.trim() == "") {
            throw new Error('First name cannot be empty');
        }
        this._firstName = value;
    }

    set lastName(value: string) {
        if (value.trim() == "") {
            throw new Error('Last name cannot be empty');
        }
        this._lastName = value;
    }

    static eid = "User 6;"

    static greet() {
        console.log(`Hello from ${this.eid}`);
    }
}
let jon = new User6();
jon.firstName = 'Jon';
jon.lastName = 'Jones';
console.log(jon, User6.eid);
User6.greet();