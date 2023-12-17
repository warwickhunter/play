console.log('Your code goes here, or here…');

function add(n1: number, n2: number, logResult: boolean, logLabel: string) { 
    const result = n1 + n2;
    if (logResult) {
        console.log(logLabel + result);
    } else { 
        return result;
    }
}

const number1 = 5;
const number2 = 2.8;
let printResult = true;
const resultLabel = "Result is: ";

add(number1, number2, printResult, resultLabel);
