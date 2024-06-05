package com.ibm.icu.impl.data;

import java.util.*;
import com.ibm.icu.util.*;

public class HolidayBundle_da_DK extends ListResourceBundle
{
    private static final Holiday[] fHolidays;
    private static final Object[][] fContents;
    
    public synchronized Object[][] getContents() {
        return HolidayBundle_da_DK.fContents;
    }
    
    static {
        fHolidays = new Holiday[] { SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(3, 30, -6, "General Prayer Day"), new SimpleHoliday(5, 5, "Constitution Day"), SimpleHoliday.CHRISTMAS_EVE, SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, SimpleHoliday.NEW_YEARS_EVE, EasterHoliday.MAUNDY_THURSDAY, EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY, EasterHoliday.ASCENSION, EasterHoliday.WHIT_MONDAY };
        fContents = new Object[][] { { "holidays", HolidayBundle_da_DK.fHolidays } };
    }
}
