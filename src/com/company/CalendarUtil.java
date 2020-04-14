package com.company;

import java.text.ParseException;
import java.util.Calendar;

public class CalendarUtil {
    public static int countMinutesBetweenDates(Calendar start, Calendar end)  {
        return (int)((end.getTimeInMillis() - start.getTimeInMillis())/60000);
    }
}
