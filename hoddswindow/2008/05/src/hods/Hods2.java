package hods;

import java.util.BitSet;

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
public class Hods2
{
    private static final int DEFAULT_MAX_AGE = 1000;

    public static void main(String[] args)
    {
        int maxAge = DEFAULT_MAX_AGE;
        if (args.length > 0)
        {
            maxAge = Integer.parseInt(args[0]);
        }
        
        // Create the set of ages of Hod and assume they are all prime
        BitSet primes = new BitSet(maxAge + 1);
        primes.set(0, maxAge, true);
        
        // Go through the ages and sieve out the composite numbers
        for (int i = 2; i < primes.size(); ++i)
        {
            for (int j = 2; (i * j) <= primes.size(); ++j)
            {
                primes.clear(i * j);
            }
        }
        
        // Now search through the squares 
        int answerCount = 0;
        for (int i = 2; (i * i) < primes.size(); ++i)
        {
            int age = i * i;
            // Is the one before the square a composite and the one before that a prime?
            if (primes.get(age - 1) == false && primes.get(age - 2))
            {
                // Yes, we have an answer
                System.out.printf("Answer, %3d is square, %3d is composite, %3d is prime %n", age, age - 1, age - 2);
                answerCount++;
            }
        }
        System.out.printf("Count of answers %d %n", answerCount);
    }
}
