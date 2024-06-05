package com.ibm.icu.impl.data;

import java.util.*;

public class HolidayBundle extends ListResourceBundle
{
    private static final Object[][] fContents;
    
    public synchronized Object[][] getContents() {
        return HolidayBundle.fContents;
    }
    
    static {
        fContents = new Object[][] { { "", "" } };
    }
}
