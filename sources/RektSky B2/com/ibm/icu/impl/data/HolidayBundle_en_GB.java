package com.ibm.icu.impl.data;

import java.util.*;
import com.ibm.icu.util.*;

public class HolidayBundle_en_GB extends ListResourceBundle
{
    private static final Holiday[] fHolidays;
    private static final Object[][] fContents;
    
    public synchronized Object[][] getContents() {
        return HolidayBundle_en_GB.fContents;
    }
    
    static {
        fHolidays = new Holiday[] { SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.MAY_DAY, new SimpleHoliday(4, 31, -2, "Spring Holiday"), new SimpleHoliday(7, 31, -2, "Summer Bank Holiday"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, new SimpleHoliday(11, 31, -2, "Christmas Holiday"), EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY };
        fContents = new Object[][] { { "holidays", HolidayBundle_en_GB.fHolidays }, { "Labor Day", "Labour Day" } };
    }
}
