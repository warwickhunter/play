function getLength(val: any[]): number; // function overload
function getLength(val: string): string; // function overload
function getLength(val: string | any[]) {
    if (typeof val === 'string') {
        const numberOfWords = val.split(' ').length;
        return `${numberOfWords} words`;
    }
    return val.length;
}

const numOfWords = getLength("does this work?");
console.log(numOfWords);
const numOfItems = getLength(['Sport', 'Cookies']);
console.log(numOfItems);