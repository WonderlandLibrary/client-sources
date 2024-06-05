package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.lang.*;
import java.text.*;
import com.ibm.icu.util.*;
import java.util.*;

public class RelativeDateFormat extends DateFormat
{
    private static final long serialVersionUID = 1131984966440549435L;
    private DateFormat fDateFormat;
    private DateFormat fTimeFormat;
    private MessageFormat fCombinedFormat;
    private SimpleDateFormat fDateTimeFormat;
    private String fDatePattern;
    private String fTimePattern;
    int fDateStyle;
    int fTimeStyle;
    ULocale fLocale;
    private transient List<URelativeString> fDates;
    private boolean combinedFormatHasDateAtStart;
    private boolean capitalizationInfoIsSet;
    private boolean capitalizationOfRelativeUnitsForListOrMenu;
    private boolean capitalizationOfRelativeUnitsForStandAlone;
    private transient BreakIterator capitalizationBrkIter;
    
    public RelativeDateFormat(final int timeStyle, final int dateStyle, final ULocale locale, final Calendar cal) {
        this.fDateTimeFormat = null;
        this.fDatePattern = null;
        this.fTimePattern = null;
        this.fDates = null;
        this.combinedFormatHasDateAtStart = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationOfRelativeUnitsForListOrMenu = false;
        this.capitalizationOfRelativeUnitsForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.calendar = cal;
        this.fLocale = locale;
        this.fTimeStyle = timeStyle;
        this.fDateStyle = dateStyle;
        if (this.fDateStyle != -1) {
            int newStyle = this.fDateStyle & 0xFFFFFF7F;
            DateFormat df = DateFormat.getDateInstance(newStyle, locale);
            if (!(df instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)df;
            this.fDatePattern = this.fDateTimeFormat.toPattern();
            if (this.fTimeStyle != -1) {
                newStyle = (this.fTimeStyle & 0xFFFFFF7F);
                df = DateFormat.getTimeInstance(newStyle, locale);
                if (df instanceof SimpleDateFormat) {
                    this.fTimePattern = ((SimpleDateFormat)df).toPattern();
                }
            }
        }
        else {
            final int newStyle = this.fTimeStyle & 0xFFFFFF7F;
            final DateFormat df = DateFormat.getTimeInstance(newStyle, locale);
            if (!(df instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)df;
            this.fTimePattern = this.fDateTimeFormat.toPattern();
        }
        this.initializeCalendar(null, this.fLocale);
        this.loadDates();
        this.initializeCombinedFormat(this.calendar, this.fLocale);
    }
    
    @Override
    public StringBuffer format(final Calendar cal, final StringBuffer toAppendTo, final FieldPosition fieldPosition) {
        String relativeDayString = null;
        final DisplayContext capitalizationContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        if (this.fDateStyle != -1) {
            final int dayDiff = dayDifference(cal);
            relativeDayString = this.getStringForDay(dayDiff);
        }
        if (this.fDateTimeFormat != null) {
            if (relativeDayString != null && this.fDatePattern != null && (this.fTimePattern == null || this.fCombinedFormat == null || this.combinedFormatHasDateAtStart)) {
                if (relativeDayString.length() > 0 && UCharacter.isLowerCase(relativeDayString.codePointAt(0)) && (capitalizationContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || (capitalizationContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu) || (capitalizationContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone))) {
                    if (this.capitalizationBrkIter == null) {
                        this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
                    }
                    relativeDayString = UCharacter.toTitleCase(this.fLocale, relativeDayString, this.capitalizationBrkIter, 768);
                }
                this.fDateTimeFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
            }
            else {
                this.fDateTimeFormat.setContext(capitalizationContext);
            }
        }
        if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
            if (this.fDatePattern == null) {
                this.fDateTimeFormat.applyPattern(this.fTimePattern);
                this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
            }
            else if (this.fTimePattern == null) {
                if (relativeDayString != null) {
                    toAppendTo.append(relativeDayString);
                }
                else {
                    this.fDateTimeFormat.applyPattern(this.fDatePattern);
                    this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
                }
            }
            else {
                String datePattern = this.fDatePattern;
                if (relativeDayString != null) {
                    datePattern = "'" + relativeDayString.replace("'", "''") + "'";
                }
                final StringBuffer combinedPattern = new StringBuffer("");
                this.fCombinedFormat.format(new Object[] { this.fTimePattern, datePattern }, combinedPattern, new FieldPosition(0));
                this.fDateTimeFormat.applyPattern(combinedPattern.toString());
                this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
            }
        }
        else if (this.fDateFormat != null) {
            if (relativeDayString != null) {
                toAppendTo.append(relativeDayString);
            }
            else {
                this.fDateFormat.format(cal, toAppendTo, fieldPosition);
            }
        }
        return toAppendTo;
    }
    
    @Override
    public void parse(final String text, final Calendar cal, final ParsePosition pos) {
        throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
    }
    
    @Override
    public void setContext(final DisplayContext context) {
        super.setContext(context);
        if (!this.capitalizationInfoIsSet && (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.fLocale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu) || (context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone))) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
        }
    }
    
    private String getStringForDay(final int day) {
        if (this.fDates == null) {
            this.loadDates();
        }
        for (final URelativeString dayItem : this.fDates) {
            if (dayItem.offset == day) {
                return dayItem.string;
            }
        }
        return null;
    }
    
    private synchronized void loadDates() {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", this.fLocale);
        this.fDates = new ArrayList<URelativeString>();
        final RelDateFmtDataSink sink = new RelDateFmtDataSink();
        rb.getAllItemsWithFallback("fields/day/relative", sink);
    }
    
    private void initCapitalizationContextInfo(final ULocale locale) {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
        try {
            final ICUResourceBundle rdb = rb.getWithFallback("contextTransforms/relative");
            final int[] intVector = rdb.getIntVector();
            if (intVector.length >= 2) {
                this.capitalizationOfRelativeUnitsForListOrMenu = (intVector[0] != 0);
                this.capitalizationOfRelativeUnitsForStandAlone = (intVector[1] != 0);
            }
        }
        catch (MissingResourceException ex) {}
    }
    
    private static int dayDifference(final Calendar until) {
        final Calendar nowCal = (Calendar)until.clone();
        final Date nowDate = new Date(System.currentTimeMillis());
        nowCal.clear();
        nowCal.setTime(nowDate);
        final int dayDiff = until.get(20) - nowCal.get(20);
        return dayDiff;
    }
    
    private Calendar initializeCalendar(final TimeZone zone, final ULocale locale) {
        if (this.calendar == null) {
            if (zone == null) {
                this.calendar = Calendar.getInstance(locale);
            }
            else {
                this.calendar = Calendar.getInstance(zone, locale);
            }
        }
        return this.calendar;
    }
    
    private MessageFormat initializeCombinedFormat(final Calendar cal, final ULocale locale) {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
        final String resourcePath = "calendar/" + cal.getType() + "/DateTimePatterns";
        ICUResourceBundle patternsRb = rb.findWithFallback(resourcePath);
        if (patternsRb == null && !cal.getType().equals("gregorian")) {
            patternsRb = rb.findWithFallback("calendar/gregorian/DateTimePatterns");
        }
        String pattern;
        if (patternsRb == null || patternsRb.getSize() < 9) {
            pattern = "{1} {0}";
        }
        else {
            int glueIndex = 8;
            if (patternsRb.getSize() >= 13) {
                if (this.fDateStyle >= 0 && this.fDateStyle <= 3) {
                    glueIndex += this.fDateStyle + 1;
                }
                else if (this.fDateStyle >= 128 && this.fDateStyle <= 131) {
                    glueIndex += this.fDateStyle + 1 - 128;
                }
            }
            final int elementType = patternsRb.get(glueIndex).getType();
            if (elementType == 8) {
                pattern = patternsRb.get(glueIndex).getString(0);
            }
            else {
                pattern = patternsRb.getString(glueIndex);
            }
        }
        this.combinedFormatHasDateAtStart = pattern.startsWith("{1}");
        return this.fCombinedFormat = new MessageFormat(pattern, locale);
    }
    
    public static class URelativeString
    {
        public int offset;
        public String string;
        
        URelativeString(final int offset, final String string) {
            this.offset = offset;
            this.string = string;
        }
        
        URelativeString(final String offset, final String string) {
            this.offset = Integer.parseInt(offset);
            this.string = string;
        }
    }
    
    private final class RelDateFmtDataSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            if (value.getType() == 3) {
                return;
            }
            final UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); ++i) {
                int keyOffset;
                try {
                    keyOffset = Integer.parseInt(key.toString());
                }
                catch (NumberFormatException nfe) {
                    return;
                }
                if (RelativeDateFormat.this.getStringForDay(keyOffset) == null) {
                    final URelativeString newDayInfo = new URelativeString(keyOffset, value.getString());
                    RelativeDateFormat.this.fDates.add(newDayInfo);
                }
            }
        }
    }
}
