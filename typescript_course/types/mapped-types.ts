type Operations = {
    add: (a: number, b: number) => number;
    subtract: (a: number, b: number) => number;
}

// type Results = {
//     add: number;
//     subtract: number;
// }

type Results<T> = {
    [Key in keyof T]? : number; // mapped type making optional
}

let mathsOperations: Operations = {
    add: (a : number, b : number): number => a + b,
    subtract: (a : number, b : number) : number => a - b
}

let mathsResults: Results<Operations> = {
    add: mathsOperations?.add(1, 2),
    subtract: mathsOperations?.subtract(5, 2),
}
console.log(mathsResults)

// optional mapping types
type Results2<T> = {
    [Key in keyof T]?: number;
}

// removing optional when mapping types
type Results3<T> = {
    [Key in keyof T]-?: number;
}

// readonly properties
type Operations2 = {
    readonly add: (a: number, b: number) => number;
    readonly subtract: (a: number, b: number) => number;
}

// removing readonly when mapping properties
type Results4<T> = {
    -readonly [Key in keyof T]?: number;
}
