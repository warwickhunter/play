package hods;

import java.util.TreeSet;

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
public class Hods3
{
    private static final int DEFAULT_MAX_AGE = 1000;

    public static void main(String[] args)
    {
        int maxAge = DEFAULT_MAX_AGE;
        if (args.length > 0)
        {
            maxAge = Integer.parseInt(args[0]);
        }
        
        // Search through the squares 
        int answerCount = 0;
        for (int i = 2; (i * i) < maxAge; ++i)
        {
            int age = i * i;
            // Is the age before the square a composite and the one before that a prime?
            if (!isPrime(age - 1) && isPrime(age - 2))
            {
                // Yes, we have an answer
                System.out.printf("Answer, %3d is square, %3d is composite, %3d is prime %n", age, age - 1, age - 2);
                answerCount++;
            }
        }
        System.out.printf("Count of answers %d %n", answerCount);
    }

    /** A cache of prime numbers we have already found */
    private static TreeSet<Integer> s_primes = new TreeSet<Integer>();
    
    /**
     * Determine if a value for the age is prime
     * @param age
     *      check if this is prime
     * @return
     *      true if prime, false if composite
     */
    private static boolean isPrime(int age)
    {
        // Look for the value in our cache of primes first
        if (s_primes.contains(age))
        {
            return true;
        }

        //
        // The cache of primes is sorted, if we find a number above this one
        // in the cache we know this number is not prime.
        //
        if (s_primes.higher(age) != null)
        {
            return false;
        }
        
        // We don't know if this one is prime or not so do the hard work to find out
        for (int i = 2; i < age; ++i)
        {
            for (int j = 2; (i * j) <= age; ++j)
            {
                if ((i * j) == age)
                {
                    return false;
                }
            }
        }
        s_primes.add(age);
        return true;
    }
}
