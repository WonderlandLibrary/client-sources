package com.ibm.icu.util;

import java.io.*;

public class TimeUnit extends MeasureUnit
{
    private static final long serialVersionUID = -2839973855554750484L;
    private final int index;
    
    TimeUnit(final String type, final String code) {
        super(type, code);
        this.index = 0;
    }
    
    public static TimeUnit[] values() {
        return new TimeUnit[] { TimeUnit.SECOND, TimeUnit.MINUTE, TimeUnit.HOUR, TimeUnit.DAY, TimeUnit.WEEK, TimeUnit.MONTH, TimeUnit.YEAR };
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnitProxy(this.type, this.subType);
    }
    
    private Object readResolve() throws ObjectStreamException {
        switch (this.index) {
            case 6: {
                return TimeUnit.SECOND;
            }
            case 5: {
                return TimeUnit.MINUTE;
            }
            case 4: {
                return TimeUnit.HOUR;
            }
            case 3: {
                return TimeUnit.DAY;
            }
            case 2: {
                return TimeUnit.WEEK;
            }
            case 1: {
                return TimeUnit.MONTH;
            }
            case 0: {
                return TimeUnit.YEAR;
            }
            default: {
                throw new InvalidObjectException("Bad index: " + this.index);
            }
        }
    }
}
