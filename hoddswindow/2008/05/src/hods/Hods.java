package hods;

/**
 * Solve the Varsity College Hods window problem.
 * 
 * If Hod lives to be a thousand years old, how many times will 
 * the following occur.
 * <ul>
 *   <li>His age is the square of a number</li>
 *   <li>His age minus one is not a prime</li>
 *   <li>Hig age minus two is a prime</li>
 * </ul>
 * 
 * @author Warwick Hunter
 * @since  2008-05-15
 */
public class Hods
{
    private static final int MAX_AGE = 1000;

    public static void main(String[] args)
    {
        // Setup the array of ages of Hod
        Number[] allAges = new Number[MAX_AGE + 1];
        for (int age = 1; age < allAges.length; ++age)
        {
            allAges[age] = new Number(age);
        }

        // Go through the ages and mark all the ages that are squares of a number
        for (int age = 2; (age * age) <= MAX_AGE; ++age)
        {
            allAges[age * age].setSquare(true);
        }
        
        // Go through the ages and sieve out the composite numbers
        for (int i = 2; i < MAX_AGE; ++i)
        {
            for (int j = 2; (i * j) <= MAX_AGE; ++j)
            {
                allAges[i * j].setPrime(false);
            }
        }
        
        // Now search through the ages and find all the squares
        int answerCount = 0;
        for (int age = 1; age < allAges.length; ++age)
        {
            if (allAges[age].isSquare())
            {
                // We have found a square, is the one before non prime and the one before that prime?
                if (allAges[age - 1].isPrime() == false && allAges[age - 2].isPrime())
                {
                    // Yes, we have an answer
                    System.out.printf("Answer, %3d is square, %3d is composite, %3d is prime %n", age, age - 1, age - 2);
                    answerCount++;
                }
            }
        }
        System.out.printf("Count of answers %d %n", answerCount);
    }
}
