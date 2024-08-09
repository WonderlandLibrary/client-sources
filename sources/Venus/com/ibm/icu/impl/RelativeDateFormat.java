/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

public class RelativeDateFormat
extends DateFormat {
    private static final long serialVersionUID = 1131984966440549435L;
    private DateFormat fDateFormat;
    private DateFormat fTimeFormat;
    private MessageFormat fCombinedFormat;
    private SimpleDateFormat fDateTimeFormat = null;
    private String fDatePattern = null;
    private String fTimePattern = null;
    int fDateStyle;
    int fTimeStyle;
    ULocale fLocale;
    private transient List<URelativeString> fDates = null;
    private boolean combinedFormatHasDateAtStart = false;
    private boolean capitalizationInfoIsSet = false;
    private boolean capitalizationOfRelativeUnitsForListOrMenu = false;
    private boolean capitalizationOfRelativeUnitsForStandAlone = false;
    private transient BreakIterator capitalizationBrkIter = null;

    public RelativeDateFormat(int n, int n2, ULocale uLocale, Calendar calendar) {
        this.calendar = calendar;
        this.fLocale = uLocale;
        this.fTimeStyle = n;
        this.fDateStyle = n2;
        if (this.fDateStyle != -1) {
            int n3 = this.fDateStyle & 0xFFFFFF7F;
            DateFormat dateFormat = DateFormat.getDateInstance(n3, uLocale);
            if (!(dateFormat instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)dateFormat;
            this.fDatePattern = this.fDateTimeFormat.toPattern();
            if (this.fTimeStyle != -1 && (dateFormat = DateFormat.getTimeInstance(n3 = this.fTimeStyle & 0xFFFFFF7F, uLocale)) instanceof SimpleDateFormat) {
                this.fTimePattern = ((SimpleDateFormat)dateFormat).toPattern();
            }
        } else {
            int n4 = this.fTimeStyle & 0xFFFFFF7F;
            DateFormat dateFormat = DateFormat.getTimeInstance(n4, uLocale);
            if (!(dateFormat instanceof SimpleDateFormat)) {
                throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
            }
            this.fDateTimeFormat = (SimpleDateFormat)dateFormat;
            this.fTimePattern = this.fDateTimeFormat.toPattern();
        }
        this.initializeCalendar(null, this.fLocale);
        this.loadDates();
        this.initializeCombinedFormat(this.calendar, this.fLocale);
    }

    @Override
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        String string = null;
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        if (this.fDateStyle != -1) {
            int n = RelativeDateFormat.dayDifference(calendar);
            string = this.getStringForDay(n);
        }
        if (this.fDateTimeFormat != null) {
            if (string != null && this.fDatePattern != null && (this.fTimePattern == null || this.fCombinedFormat == null || this.combinedFormatHasDateAtStart)) {
                if (string.length() > 0 && UCharacter.isLowerCase(string.codePointAt(0)) && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)) {
                    if (this.capitalizationBrkIter == null) {
                        this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
                    }
                    string = UCharacter.toTitleCase(this.fLocale, string, this.capitalizationBrkIter, 768);
                }
                this.fDateTimeFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
            } else {
                this.fDateTimeFormat.setContext(displayContext);
            }
        }
        if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
            if (this.fDatePattern == null) {
                this.fDateTimeFormat.applyPattern(this.fTimePattern);
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            } else if (this.fTimePattern == null) {
                if (string != null) {
                    stringBuffer.append(string);
                } else {
                    this.fDateTimeFormat.applyPattern(this.fDatePattern);
                    this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
                }
            } else {
                String string2 = this.fDatePattern;
                if (string != null) {
                    string2 = "'" + string.replace("'", "''") + "'";
                }
                StringBuffer stringBuffer2 = new StringBuffer("");
                this.fCombinedFormat.format(new Object[]{this.fTimePattern, string2}, stringBuffer2, new FieldPosition(0));
                this.fDateTimeFormat.applyPattern(stringBuffer2.toString());
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            }
        } else if (this.fDateFormat != null) {
            if (string != null) {
                stringBuffer.append(string);
            } else {
                this.fDateFormat.format(calendar, stringBuffer, fieldPosition);
            }
        }
        return stringBuffer;
    }

    @Override
    public void parse(String string, Calendar calendar, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (!(this.capitalizationInfoIsSet || displayContext != DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && displayContext != DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.fLocale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
        }
    }

    private String getStringForDay(int n) {
        if (this.fDates == null) {
            this.loadDates();
        }
        for (URelativeString uRelativeString : this.fDates) {
            if (uRelativeString.offset != n) continue;
            return uRelativeString.string;
        }
        return null;
    }

    private synchronized void loadDates() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", this.fLocale);
        this.fDates = new ArrayList<URelativeString>();
        RelDateFmtDataSink relDateFmtDataSink = new RelDateFmtDataSink(this, null);
        iCUResourceBundle.getAllItemsWithFallback("fields/day/relative", relDateFmtDataSink);
    }

    private void initCapitalizationContextInfo(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        try {
            ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.getWithFallback("contextTransforms/relative");
            int[] nArray = iCUResourceBundle2.getIntVector();
            if (nArray.length >= 2) {
                this.capitalizationOfRelativeUnitsForListOrMenu = nArray[0] != 0;
                this.capitalizationOfRelativeUnitsForStandAlone = nArray[1] != 0;
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    private static int dayDifference(Calendar calendar) {
        Calendar calendar2 = (Calendar)calendar.clone();
        Date date = new Date(System.currentTimeMillis());
        calendar2.clear();
        calendar2.setTime(date);
        int n = calendar.get(20) - calendar2.get(20);
        return n;
    }

    private Calendar initializeCalendar(TimeZone timeZone, ULocale uLocale) {
        if (this.calendar == null) {
            this.calendar = timeZone == null ? Calendar.getInstance(uLocale) : Calendar.getInstance(timeZone, uLocale);
        }
        return this.calendar;
    }

    private MessageFormat initializeCombinedFormat(Calendar calendar, ULocale uLocale) {
        String string;
        String string2;
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback(string2 = "calendar/" + calendar.getType() + "/DateTimePatterns");
        if (iCUResourceBundle2 == null && !calendar.getType().equals("gregorian")) {
            iCUResourceBundle2 = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
        }
        if (iCUResourceBundle2 == null || iCUResourceBundle2.getSize() < 9) {
            string = "{1} {0}";
        } else {
            int n;
            int n2 = 8;
            if (iCUResourceBundle2.getSize() >= 13) {
                if (this.fDateStyle >= 0 && this.fDateStyle <= 3) {
                    n2 += this.fDateStyle + 1;
                } else if (this.fDateStyle >= 128 && this.fDateStyle <= 131) {
                    n2 += this.fDateStyle + 1 - 128;
                }
            }
            string = (n = iCUResourceBundle2.get(n2).getType()) == 8 ? iCUResourceBundle2.get(n2).getString(0) : iCUResourceBundle2.getString(n2);
        }
        this.combinedFormatHasDateAtStart = string.startsWith("{1}");
        this.fCombinedFormat = new MessageFormat(string, uLocale);
        return this.fCombinedFormat;
    }

    static String access$000(RelativeDateFormat relativeDateFormat, int n) {
        return relativeDateFormat.getStringForDay(n);
    }

    static List access$100(RelativeDateFormat relativeDateFormat) {
        return relativeDateFormat.fDates;
    }

    private final class RelDateFmtDataSink
    extends UResource.Sink {
        final RelativeDateFormat this$0;

        private RelDateFmtDataSink(RelativeDateFormat relativeDateFormat) {
            this.this$0 = relativeDateFormat;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                try {
                    n2 = Integer.parseInt(key.toString());
                } catch (NumberFormatException numberFormatException) {
                    return;
                }
                if (RelativeDateFormat.access$000(this.this$0, n2) == null) {
                    URelativeString uRelativeString = new URelativeString(n2, value.getString());
                    RelativeDateFormat.access$100(this.this$0).add(uRelativeString);
                }
                ++n;
            }
        }

        RelDateFmtDataSink(RelativeDateFormat relativeDateFormat, 1 var2_2) {
            this(relativeDateFormat);
        }
    }

    public static class URelativeString {
        public int offset;
        public String string;

        URelativeString(int n, String string) {
            this.offset = n;
            this.string = string;
        }

        URelativeString(String string, String string2) {
            this.offset = Integer.parseInt(string);
            this.string = string2;
        }
    }
}

