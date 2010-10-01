/**
 * Copyright Warwick Hunter 2010. All rights reserved.
 */
package org.computer.whunter;

import java.util.Calendar;

/**
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2010-10-01
 */
public class DateTimeBean
{
    private final Calendar m_calendar = Calendar.getInstance();
    
    public String getDateTime()
    {
        return String.format("%1$tF %1$tT", m_calendar);
    }
}
