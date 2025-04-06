type InvestmentData = {
  initialAmount: number;
  annualContribution: number;
  expectedReturn: number;
  duration: number;
};

type InvestmentResult = {
    year: string;
    totalAmount: number;
    totalContributions: number;
    totalInterestEarned: number;
}

type CalculationResult = Array<InvestmentResult> | string;

function calculateInvestment(data: InvestmentData): CalculationResult {
    const { initialAmount, annualContribution, expectedReturn, duration } = data;

    if (initialAmount < 0) {
        return "Initial amount cannot be negative";
    }
    if (duration <= 0) {
        return "Duration cannot be negative or zero";
    }
    if (expectedReturn < 0) {
        return "Expected return cannot be negative";
    }

    let total = initialAmount;
    let totalContributions = 0;
    let totalInterestEarned = 0;

    const annualResults: InvestmentResult[] = [];

    for (let i = 0; i < duration; i++) {
        total = total * (1 + expectedReturn);
        totalInterestEarned = total - totalContributions - initialAmount;
        totalContributions = totalContributions + annualContribution;
        annualResults.push(
            {
                year: `Year ${i + 1}`,
                totalAmount: total,
                totalContributions,
                totalInterestEarned
            }
        )
    }

    return annualResults
}

function printResults(results: CalculationResult) {
    if (typeof results === "string") {
        console.log(results);
        return;
    }
    for (const result of results) {
        console.log(result.year);
        console.log(`Total: ${result.totalAmount.toFixed(0)}`)
        console.log(`Total Contributions: ${result.totalContributions.toFixed(0)}`)
        console.log(`Total Interest Earned: ${result.totalInterestEarned.toFixed(0)}`)
        console.log("----------------------------------------------------------------")
    }
}

const investmentData : InvestmentData = {
    initialAmount: 5000,
    annualContribution: 500,
    expectedReturn: 0.08,
    duration: 10
}

const results = calculateInvestment(investmentData);

printResults(results);