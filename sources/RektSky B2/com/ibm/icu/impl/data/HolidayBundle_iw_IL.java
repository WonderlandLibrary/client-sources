package com.ibm.icu.impl.data;

import java.util.*;
import com.ibm.icu.util.*;

public class HolidayBundle_iw_IL extends ListResourceBundle
{
    private static final Holiday[] fHolidays;
    private static final Object[][] fContents;
    
    public synchronized Object[][] getContents() {
        return HolidayBundle_iw_IL.fContents;
    }
    
    static {
        fHolidays = new Holiday[] { HebrewHoliday.ROSH_HASHANAH, HebrewHoliday.YOM_KIPPUR, HebrewHoliday.HANUKKAH, HebrewHoliday.PURIM, HebrewHoliday.PASSOVER, HebrewHoliday.SHAVUOT, HebrewHoliday.SELIHOT };
        fContents = new Object[][] { { "holidays", HolidayBundle_iw_IL.fHolidays } };
    }
}
