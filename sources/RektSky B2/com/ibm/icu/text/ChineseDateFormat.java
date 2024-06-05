package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import java.text.*;
import java.io.*;

@Deprecated
public class ChineseDateFormat extends SimpleDateFormat
{
    static final long serialVersionUID = -4610300753104099899L;
    
    @Deprecated
    public ChineseDateFormat(final String pattern, final Locale locale) {
        this(pattern, ULocale.forLocale(locale));
    }
    
    @Deprecated
    public ChineseDateFormat(final String pattern, final ULocale locale) {
        this(pattern, (String)null, locale);
    }
    
    @Deprecated
    public ChineseDateFormat(final String pattern, final String override, final ULocale locale) {
        super(pattern, new ChineseDateFormatSymbols(locale), new ChineseCalendar(TimeZone.getDefault(), locale), locale, true, override);
    }
    
    @Deprecated
    @Override
    protected void subFormat(final StringBuffer buf, final char ch, final int count, final int beginOffset, final int fieldNum, final DisplayContext capitalizationContext, final FieldPosition pos, final Calendar cal) {
        super.subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
    }
    
    @Deprecated
    @Override
    protected int subParse(final String text, final int start, final char ch, final int count, final boolean obeyCount, final boolean allowNegative, final boolean[] ambiguousYear, final Calendar cal) {
        return super.subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal);
    }
    
    @Deprecated
    @Override
    protected DateFormat.Field patternCharToDateFormatField(final char ch) {
        return super.patternCharToDateFormatField(ch);
    }
    
    @Deprecated
    public static class Field extends DateFormat.Field
    {
        private static final long serialVersionUID = -5102130532751400330L;
        @Deprecated
        public static final Field IS_LEAP_MONTH;
        
        @Deprecated
        protected Field(final String name, final int calendarField) {
            super(name, calendarField);
        }
        
        @Deprecated
        public static DateFormat.Field ofCalendarField(final int calendarField) {
            if (calendarField == 22) {
                return Field.IS_LEAP_MONTH;
            }
            return DateFormat.Field.ofCalendarField(calendarField);
        }
        
        @Deprecated
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
            }
            if (this.getName().equals(Field.IS_LEAP_MONTH.getName())) {
                return Field.IS_LEAP_MONTH;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
        
        static {
            IS_LEAP_MONTH = new Field("is leap month", 22);
        }
    }
}
