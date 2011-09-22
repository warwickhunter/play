package hods;

/**
 * A number with a value and flags to indicate primeness and squareness.
 * 
 * @author Warwick Hunter
 * @since  2008-05-15
 */
public class Number
{
    private boolean m_isPrime;
    private boolean m_isSquare;
    private int     m_value;
    
    public Number(int value)
    {
        m_value    = value;
        m_isSquare = false;
        m_isPrime  = true;
    }

    public boolean isPrime()
    {
        return m_isPrime;
    }

    public void setPrime(boolean isPrime)
    {
        m_isPrime = isPrime;
    }

    public boolean isSquare()
    {
        return m_isSquare;
    }

    public void setSquare(boolean isSquare)
    {
        m_isSquare = isSquare;
    }

    public int getValue()
    {
        return m_value;
    }

    public void setValue(int number)
    {
        m_value = number;
    }
}

