// npm install @types/node --save-dev
// import fs from 'node:fs';

console.log('Hello World');

let userName: string;
userName = "Ted";

function add(a: number, b:number | null): number {
    return a + (b ?? 0);
}