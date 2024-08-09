/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GlobalizationPreferences
implements Freezable<GlobalizationPreferences> {
    public static final int NF_NUMBER = 0;
    public static final int NF_CURRENCY = 1;
    public static final int NF_PERCENT = 2;
    public static final int NF_SCIENTIFIC = 3;
    public static final int NF_INTEGER = 4;
    private static final int NF_LIMIT = 5;
    public static final int DF_FULL = 0;
    public static final int DF_LONG = 1;
    public static final int DF_MEDIUM = 2;
    public static final int DF_SHORT = 3;
    public static final int DF_NONE = 4;
    private static final int DF_LIMIT = 5;
    public static final int ID_LOCALE = 0;
    public static final int ID_LANGUAGE = 1;
    public static final int ID_SCRIPT = 2;
    public static final int ID_TERRITORY = 3;
    public static final int ID_VARIANT = 4;
    public static final int ID_KEYWORD = 5;
    public static final int ID_KEYWORD_VALUE = 6;
    public static final int ID_CURRENCY = 7;
    public static final int ID_CURRENCY_SYMBOL = 8;
    public static final int ID_TIMEZONE = 9;
    public static final int BI_CHARACTER = 0;
    public static final int BI_WORD = 1;
    public static final int BI_LINE = 2;
    public static final int BI_SENTENCE = 3;
    public static final int BI_TITLE = 4;
    private static final int BI_LIMIT = 5;
    private List<ULocale> locales;
    private String territory;
    private Currency currency;
    private TimeZone timezone;
    private Calendar calendar;
    private Collator collator;
    private BreakIterator[] breakIterators;
    private DateFormat[][] dateFormats;
    private NumberFormat[] numberFormats;
    private List<ULocale> implicitLocales;
    private static final HashMap<ULocale, BitSet> available_locales;
    private static final int TYPE_GENERIC = 0;
    private static final int TYPE_CALENDAR = 1;
    private static final int TYPE_DATEFORMAT = 2;
    private static final int TYPE_NUMBERFORMAT = 3;
    private static final int TYPE_COLLATOR = 4;
    private static final int TYPE_BREAKITERATOR = 5;
    private static final int TYPE_LIMIT = 6;
    private static final Map<String, String> language_territory_hack_map;
    private static final String[][] language_territory_hack;
    static final Map<String, String> territory_tzid_hack_map;
    static final String[][] territory_tzid_hack;
    private volatile boolean frozen;

    public GlobalizationPreferences() {
        this.reset();
    }

    public GlobalizationPreferences setLocales(List<ULocale> list) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = this.processLocales(list);
        return this;
    }

    public List<ULocale> getLocales() {
        List<ULocale> list;
        if (this.locales == null) {
            list = this.guessLocales();
        } else {
            list = new ArrayList<ULocale>();
            list.addAll(this.locales);
        }
        return list;
    }

    public ULocale getLocale(int n) {
        List<ULocale> list = this.locales;
        if (list == null) {
            list = this.guessLocales();
        }
        if (n >= 0 && n < list.size()) {
            return list.get(n);
        }
        return null;
    }

    public GlobalizationPreferences setLocales(ULocale[] uLocaleArray) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return this.setLocales(Arrays.asList(uLocaleArray));
    }

    public GlobalizationPreferences setLocale(ULocale uLocale) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return this.setLocales(new ULocale[]{uLocale});
    }

    public GlobalizationPreferences setLocales(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        ULocale[] uLocaleArray = null;
        try {
            uLocaleArray = ULocale.parseAcceptLanguage(string, true);
        } catch (ParseException parseException) {
            throw new IllegalArgumentException("Invalid Accept-Language string");
        }
        return this.setLocales(uLocaleArray);
    }

    public ResourceBundle getResourceBundle(String string) {
        return this.getResourceBundle(string, null);
    }

    public ResourceBundle getResourceBundle(String string, ClassLoader classLoader) {
        UResourceBundle uResourceBundle = null;
        UResourceBundle uResourceBundle2 = null;
        String string2 = null;
        List<ULocale> list = this.getLocales();
        for (int i = 0; i < list.size(); ++i) {
            String string3 = list.get(i).toString();
            if (string2 != null && string3.equals(string2)) {
                uResourceBundle = uResourceBundle2;
                break;
            }
            try {
                uResourceBundle2 = classLoader == null ? UResourceBundle.getBundleInstance(string, string3) : UResourceBundle.getBundleInstance(string, string3, classLoader);
                if (uResourceBundle2 == null) continue;
                string2 = uResourceBundle2.getULocale().getName();
                if (string2.equals(string3)) {
                    uResourceBundle = uResourceBundle2;
                    break;
                }
                if (uResourceBundle != null) continue;
                uResourceBundle = uResourceBundle2;
                continue;
            } catch (MissingResourceException missingResourceException) {
                string2 = null;
            }
        }
        if (uResourceBundle == null) {
            throw new MissingResourceException("Can't find bundle for base name " + string, string, "");
        }
        return uResourceBundle;
    }

    public GlobalizationPreferences setTerritory(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.territory = string;
        return this;
    }

    public String getTerritory() {
        if (this.territory == null) {
            return this.guessTerritory();
        }
        return this.territory;
    }

    public GlobalizationPreferences setCurrency(Currency currency) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.currency = currency;
        return this;
    }

    public Currency getCurrency() {
        if (this.currency == null) {
            return this.guessCurrency();
        }
        return this.currency;
    }

    public GlobalizationPreferences setCalendar(Calendar calendar) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.calendar = (Calendar)calendar.clone();
        return this;
    }

    public Calendar getCalendar() {
        if (this.calendar == null) {
            return this.guessCalendar();
        }
        Calendar calendar = (Calendar)this.calendar.clone();
        calendar.setTimeZone(this.getTimeZone());
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }

    public GlobalizationPreferences setTimeZone(TimeZone timeZone) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.timezone = (TimeZone)timeZone.clone();
        return this;
    }

    public TimeZone getTimeZone() {
        if (this.timezone == null) {
            return this.guessTimeZone();
        }
        return this.timezone.cloneAsThawed();
    }

    public Collator getCollator() {
        if (this.collator == null) {
            return this.guessCollator();
        }
        try {
            return (Collator)this.collator.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", cloneNotSupportedException);
        }
    }

    public GlobalizationPreferences setCollator(Collator collator) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        try {
            this.collator = (Collator)collator.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", cloneNotSupportedException);
        }
        return this;
    }

    public BreakIterator getBreakIterator(int n) {
        if (n < 0 || n >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        if (this.breakIterators == null || this.breakIterators[n] == null) {
            return this.guessBreakIterator(n);
        }
        return (BreakIterator)this.breakIterators[n].clone();
    }

    public GlobalizationPreferences setBreakIterator(int n, BreakIterator breakIterator) {
        if (n < 0 || n >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.breakIterators == null) {
            this.breakIterators = new BreakIterator[5];
        }
        this.breakIterators[n] = (BreakIterator)breakIterator.clone();
        return this;
    }

    public String getDisplayName(String string, int n) {
        String string2 = string;
        block11: for (ULocale uLocale : this.getLocales()) {
            if (!this.isAvailableLocale(uLocale, 0)) continue;
            switch (n) {
                case 0: {
                    string2 = ULocale.getDisplayName(string, uLocale);
                    break;
                }
                case 1: {
                    string2 = ULocale.getDisplayLanguage(string, uLocale);
                    break;
                }
                case 2: {
                    string2 = ULocale.getDisplayScript("und-" + string, uLocale);
                    break;
                }
                case 3: {
                    string2 = ULocale.getDisplayCountry("und-" + string, uLocale);
                    break;
                }
                case 4: {
                    string2 = ULocale.getDisplayVariant("und-QQ-" + string, uLocale);
                    break;
                }
                case 5: {
                    string2 = ULocale.getDisplayKeyword(string, uLocale);
                    break;
                }
                case 6: {
                    String[] stringArray = new String[2];
                    Utility.split(string, '=', stringArray);
                    string2 = ULocale.getDisplayKeywordValue("und@" + string, stringArray[0], uLocale);
                    if (!string2.equals(stringArray[5])) break;
                    continue block11;
                }
                case 7: 
                case 8: {
                    Currency currency = new Currency(string);
                    string2 = currency.getName(uLocale, n == 7 ? 1 : 0, null);
                    break;
                }
                case 9: {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("vvvv", uLocale);
                    simpleDateFormat.setTimeZone(TimeZone.getFrozenTimeZone(string));
                    string2 = simpleDateFormat.format(new Date());
                    boolean bl = false;
                    String string3 = string2;
                    int n2 = string2.indexOf(40);
                    int n3 = string2.indexOf(41);
                    if (n2 != -1 && n3 != -1 && n3 - n2 == 3) {
                        string3 = string2.substring(n2 + 1, n3);
                    }
                    if (string3.length() == 2) {
                        bl = true;
                        for (int i = 0; i < 2; ++i) {
                            char c = string3.charAt(i);
                            if (c >= 'A' && 'Z' >= c) continue;
                            bl = false;
                            break;
                        }
                    }
                    if (!bl) break;
                    continue block11;
                }
                default: {
                    throw new IllegalArgumentException("Unknown type: " + n);
                }
            }
            if (string.equals(string2)) continue;
            return string2;
        }
        return string2;
    }

    public GlobalizationPreferences setDateFormat(int n, int n2, DateFormat dateFormat) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.dateFormats == null) {
            this.dateFormats = new DateFormat[5][5];
        }
        this.dateFormats[n][n2] = (DateFormat)dateFormat.clone();
        return this;
    }

    public DateFormat getDateFormat(int n, int n2) {
        if (n == 4 && n2 == 4 || n < 0 || n >= 5 || n2 < 0 || n2 >= 5) {
            throw new IllegalArgumentException("Illegal date format style arguments");
        }
        DateFormat dateFormat = null;
        if (this.dateFormats != null) {
            dateFormat = this.dateFormats[n][n2];
        }
        if (dateFormat != null) {
            dateFormat = (DateFormat)dateFormat.clone();
            dateFormat.setTimeZone(this.getTimeZone());
        } else {
            dateFormat = this.guessDateFormat(n, n2);
        }
        return dateFormat;
    }

    public NumberFormat getNumberFormat(int n) {
        if (n < 0 || n >= 5) {
            throw new IllegalArgumentException("Illegal number format type");
        }
        NumberFormat numberFormat = null;
        if (this.numberFormats != null) {
            numberFormat = this.numberFormats[n];
        }
        numberFormat = numberFormat != null ? (NumberFormat)numberFormat.clone() : this.guessNumberFormat(n);
        return numberFormat;
    }

    public GlobalizationPreferences setNumberFormat(int n, NumberFormat numberFormat) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.numberFormats == null) {
            this.numberFormats = new NumberFormat[5];
        }
        this.numberFormats[n] = (NumberFormat)numberFormat.clone();
        return this;
    }

    public GlobalizationPreferences reset() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = null;
        this.territory = null;
        this.calendar = null;
        this.collator = null;
        this.breakIterators = null;
        this.timezone = null;
        this.currency = null;
        this.dateFormats = null;
        this.numberFormats = null;
        this.implicitLocales = null;
        return this;
    }

    protected List<ULocale> processLocales(List<ULocale> list) {
        ULocale uLocale;
        int n;
        ArrayList<ULocale> arrayList = new ArrayList<ULocale>();
        for (n = 0; n < list.size(); ++n) {
            uLocale = list.get(n);
            String string = uLocale.getLanguage();
            String string2 = uLocale.getScript();
            String string3 = uLocale.getCountry();
            String string4 = uLocale.getVariant();
            boolean bl = false;
            for (int i = 0; i < arrayList.size(); ++i) {
                ULocale uLocale2 = (ULocale)arrayList.get(i);
                if (!uLocale2.getLanguage().equals(string)) continue;
                String string5 = uLocale2.getScript();
                String string6 = uLocale2.getCountry();
                String string7 = uLocale2.getVariant();
                if (!string5.equals(string2)) {
                    if (string5.length() == 0 && string6.length() == 0 && string7.length() == 0) {
                        arrayList.add(i, uLocale);
                        bl = true;
                        break;
                    }
                    if (string5.length() == 0 && string6.equals(string3)) {
                        arrayList.add(i, uLocale);
                        bl = true;
                        break;
                    }
                    if (string2.length() != 0 || string3.length() <= 0 || string6.length() != 0) continue;
                    arrayList.add(i, uLocale);
                    bl = true;
                    break;
                }
                if (!string6.equals(string3) && string6.length() == 0 && string7.length() == 0) {
                    arrayList.add(i, uLocale);
                    bl = true;
                    break;
                }
                if (string7.equals(string4) || string7.length() != 0) continue;
                arrayList.add(i, uLocale);
                bl = true;
                break;
            }
            if (bl) continue;
            arrayList.add(uLocale);
        }
        for (n = 0; n < arrayList.size(); ++n) {
            uLocale = (ULocale)arrayList.get(n);
            while ((uLocale = uLocale.getFallback()) != null && uLocale.getLanguage().length() != 0) {
                arrayList.add(++n, uLocale);
            }
        }
        n = 0;
        while (n < arrayList.size() - 1) {
            uLocale = (ULocale)arrayList.get(n);
            boolean bl = false;
            for (int i = n + 1; i < arrayList.size(); ++i) {
                if (!uLocale.equals(arrayList.get(i))) continue;
                arrayList.remove(n);
                bl = true;
                break;
            }
            if (bl) continue;
            ++n;
        }
        return arrayList;
    }

    protected DateFormat guessDateFormat(int n, int n2) {
        ULocale uLocale = this.getAvailableLocale(2);
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        DateFormat dateFormat = n2 == 4 ? DateFormat.getDateInstance(this.getCalendar(), n, uLocale) : (n == 4 ? DateFormat.getTimeInstance(this.getCalendar(), n2, uLocale) : DateFormat.getDateTimeInstance(this.getCalendar(), n, n2, uLocale));
        return dateFormat;
    }

    protected NumberFormat guessNumberFormat(int n) {
        NumberFormat numberFormat;
        ULocale uLocale = this.getAvailableLocale(3);
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        switch (n) {
            case 0: {
                numberFormat = NumberFormat.getInstance(uLocale);
                break;
            }
            case 3: {
                numberFormat = NumberFormat.getScientificInstance(uLocale);
                break;
            }
            case 4: {
                numberFormat = NumberFormat.getIntegerInstance(uLocale);
                break;
            }
            case 2: {
                numberFormat = NumberFormat.getPercentInstance(uLocale);
                break;
            }
            case 1: {
                numberFormat = NumberFormat.getCurrencyInstance(uLocale);
                numberFormat.setCurrency(this.getCurrency());
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown number format style");
            }
        }
        return numberFormat;
    }

    protected String guessTerritory() {
        String string;
        for (ULocale object2 : this.getLocales()) {
            string = object2.getCountry();
            if (string.length() == 0) continue;
            return string;
        }
        ULocale uLocale = this.getLocale(0);
        String string2 = uLocale.getLanguage();
        String string3 = uLocale.getScript();
        string = null;
        if (string3.length() != 0) {
            string = language_territory_hack_map.get(string2 + "_" + string3);
        }
        if (string == null) {
            string = language_territory_hack_map.get(string2);
        }
        if (string == null) {
            string = "US";
        }
        return string;
    }

    protected Currency guessCurrency() {
        return Currency.getInstance(new ULocale("und-" + this.getTerritory()));
    }

    protected List<ULocale> guessLocales() {
        if (this.implicitLocales == null) {
            ArrayList<ULocale> arrayList = new ArrayList<ULocale>(1);
            arrayList.add(ULocale.getDefault());
            this.implicitLocales = this.processLocales(arrayList);
        }
        return this.implicitLocales;
    }

    protected Collator guessCollator() {
        ULocale uLocale = this.getAvailableLocale(4);
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        return Collator.getInstance(uLocale);
    }

    protected BreakIterator guessBreakIterator(int n) {
        BreakIterator breakIterator = null;
        ULocale uLocale = this.getAvailableLocale(5);
        if (uLocale == null) {
            uLocale = ULocale.ROOT;
        }
        switch (n) {
            case 0: {
                breakIterator = BreakIterator.getCharacterInstance(uLocale);
                break;
            }
            case 4: {
                breakIterator = BreakIterator.getTitleInstance(uLocale);
                break;
            }
            case 1: {
                breakIterator = BreakIterator.getWordInstance(uLocale);
                break;
            }
            case 2: {
                breakIterator = BreakIterator.getLineInstance(uLocale);
                break;
            }
            case 3: {
                breakIterator = BreakIterator.getSentenceInstance(uLocale);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown break iterator type");
            }
        }
        return breakIterator;
    }

    protected TimeZone guessTimeZone() {
        String string = territory_tzid_hack_map.get(this.getTerritory());
        if (string == null) {
            String[] stringArray = TimeZone.getAvailableIDs(this.getTerritory());
            if (stringArray.length == 0) {
                string = "Etc/GMT";
            } else {
                int n;
                for (n = 0; n < stringArray.length && stringArray[n].indexOf("/") < 0; ++n) {
                }
                if (n > stringArray.length) {
                    n = 0;
                }
                string = stringArray[n];
            }
        }
        return TimeZone.getTimeZone(string);
    }

    protected Calendar guessCalendar() {
        ULocale uLocale = this.getAvailableLocale(1);
        if (uLocale == null) {
            uLocale = ULocale.US;
        }
        return Calendar.getInstance(this.getTimeZone(), uLocale);
    }

    private ULocale getAvailableLocale(int n) {
        List<ULocale> list = this.getLocales();
        ULocale uLocale = null;
        for (int i = 0; i < list.size(); ++i) {
            ULocale uLocale2 = list.get(i);
            if (!this.isAvailableLocale(uLocale2, n)) continue;
            uLocale = uLocale2;
            break;
        }
        return uLocale;
    }

    private boolean isAvailableLocale(ULocale uLocale, int n) {
        BitSet bitSet = available_locales.get(uLocale);
        return bitSet == null || !bitSet.get(n);
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override
    public GlobalizationPreferences freeze() {
        this.frozen = true;
        return this;
    }

    @Override
    public GlobalizationPreferences cloneAsThawed() {
        try {
            GlobalizationPreferences globalizationPreferences = (GlobalizationPreferences)this.clone();
            globalizationPreferences.frozen = false;
            return globalizationPreferences;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static {
        int n;
        BitSet bitSet;
        available_locales = new HashMap();
        ULocale[] uLocaleArray = ULocale.getAvailableLocales();
        for (int i = 0; i < uLocaleArray.length; ++i) {
            bitSet = new BitSet(6);
            available_locales.put(uLocaleArray[i], bitSet);
            bitSet.set(0);
        }
        ULocale[] uLocaleArray2 = Calendar.getAvailableULocales();
        for (int i = 0; i < uLocaleArray2.length; ++i) {
            bitSet = available_locales.get(uLocaleArray2[i]);
            if (bitSet == null) {
                bitSet = new BitSet(6);
                available_locales.put(uLocaleArray[i], bitSet);
            }
            bitSet.set(1);
        }
        ULocale[] uLocaleArray3 = DateFormat.getAvailableULocales();
        for (int i = 0; i < uLocaleArray3.length; ++i) {
            bitSet = available_locales.get(uLocaleArray3[i]);
            if (bitSet == null) {
                bitSet = new BitSet(6);
                available_locales.put(uLocaleArray[i], bitSet);
            }
            bitSet.set(2);
        }
        ULocale[] uLocaleArray4 = NumberFormat.getAvailableULocales();
        for (int i = 0; i < uLocaleArray4.length; ++i) {
            bitSet = available_locales.get(uLocaleArray4[i]);
            if (bitSet == null) {
                bitSet = new BitSet(6);
                available_locales.put(uLocaleArray[i], bitSet);
            }
            bitSet.set(3);
        }
        ULocale[] uLocaleArray5 = Collator.getAvailableULocales();
        for (int i = 0; i < uLocaleArray5.length; ++i) {
            bitSet = available_locales.get(uLocaleArray5[i]);
            if (bitSet == null) {
                bitSet = new BitSet(6);
                available_locales.put(uLocaleArray[i], bitSet);
            }
            bitSet.set(4);
        }
        ULocale[] uLocaleArray6 = BreakIterator.getAvailableULocales();
        for (int i = 0; i < uLocaleArray6.length; ++i) {
            bitSet = available_locales.get(uLocaleArray6[i]);
            bitSet.set(5);
        }
        language_territory_hack_map = new HashMap<String, String>();
        language_territory_hack = new String[][]{{"af", "ZA"}, {"am", "ET"}, {"ar", "SA"}, {"as", "IN"}, {"ay", "PE"}, {"az", "AZ"}, {"bal", "PK"}, {"be", "BY"}, {"bg", "BG"}, {"bn", "IN"}, {"bs", "BA"}, {"ca", "ES"}, {"ch", "MP"}, {"cpe", "SL"}, {"cs", "CZ"}, {"cy", "GB"}, {"da", "DK"}, {"de", "DE"}, {"dv", "MV"}, {"dz", "BT"}, {"el", "GR"}, {"en", "US"}, {"es", "ES"}, {"et", "EE"}, {"eu", "ES"}, {"fa", "IR"}, {"fi", "FI"}, {"fil", "PH"}, {"fj", "FJ"}, {"fo", "FO"}, {"fr", "FR"}, {"ga", "IE"}, {"gd", "GB"}, {"gl", "ES"}, {"gn", "PY"}, {"gu", "IN"}, {"gv", "GB"}, {"ha", "NG"}, {"he", "IL"}, {"hi", "IN"}, {"ho", "PG"}, {"hr", "HR"}, {"ht", "HT"}, {"hu", "HU"}, {"hy", "AM"}, {"id", "ID"}, {"is", "IS"}, {"it", "IT"}, {"ja", "JP"}, {"ka", "GE"}, {"kk", "KZ"}, {"kl", "GL"}, {"km", "KH"}, {"kn", "IN"}, {"ko", "KR"}, {"kok", "IN"}, {"ks", "IN"}, {"ku", "TR"}, {"ky", "KG"}, {"la", "VA"}, {"lb", "LU"}, {"ln", "CG"}, {"lo", "LA"}, {"lt", "LT"}, {"lv", "LV"}, {"mai", "IN"}, {"men", "GN"}, {"mg", "MG"}, {"mh", "MH"}, {"mk", "MK"}, {"ml", "IN"}, {"mn", "MN"}, {"mni", "IN"}, {"mo", "MD"}, {"mr", "IN"}, {"ms", "MY"}, {"mt", "MT"}, {"my", "MM"}, {"na", "NR"}, {"nb", "NO"}, {"nd", "ZA"}, {"ne", "NP"}, {"niu", "NU"}, {"nl", "NL"}, {"nn", "NO"}, {"no", "NO"}, {"nr", "ZA"}, {"nso", "ZA"}, {"ny", "MW"}, {"om", "KE"}, {"or", "IN"}, {"pa", "IN"}, {"pau", "PW"}, {"pl", "PL"}, {"ps", "PK"}, {"pt", "BR"}, {"qu", "PE"}, {"rn", "BI"}, {"ro", "RO"}, {"ru", "RU"}, {"rw", "RW"}, {"sd", "IN"}, {"sg", "CF"}, {"si", "LK"}, {"sk", "SK"}, {"sl", "SI"}, {"sm", "WS"}, {"so", "DJ"}, {"sq", "CS"}, {"sr", "CS"}, {"ss", "ZA"}, {"st", "ZA"}, {"sv", "SE"}, {"sw", "KE"}, {"ta", "IN"}, {"te", "IN"}, {"tem", "SL"}, {"tet", "TL"}, {"th", "TH"}, {"ti", "ET"}, {"tg", "TJ"}, {"tk", "TM"}, {"tkl", "TK"}, {"tvl", "TV"}, {"tl", "PH"}, {"tn", "ZA"}, {"to", "TO"}, {"tpi", "PG"}, {"tr", "TR"}, {"ts", "ZA"}, {"uk", "UA"}, {"ur", "IN"}, {"uz", "UZ"}, {"ve", "ZA"}, {"vi", "VN"}, {"wo", "SN"}, {"xh", "ZA"}, {"zh", "CN"}, {"zh_Hant", "TW"}, {"zu", "ZA"}, {"aa", "ET"}, {"byn", "ER"}, {"eo", "DE"}, {"gez", "ET"}, {"haw", "US"}, {"iu", "CA"}, {"kw", "GB"}, {"sa", "IN"}, {"sh", "HR"}, {"sid", "ET"}, {"syr", "SY"}, {"tig", "ER"}, {"tt", "RU"}, {"wal", "ET"}};
        for (n = 0; n < language_territory_hack.length; ++n) {
            language_territory_hack_map.put(language_territory_hack[n][0], language_territory_hack[n][1]);
        }
        territory_tzid_hack_map = new HashMap<String, String>();
        territory_tzid_hack = new String[][]{{"AQ", "Antarctica/McMurdo"}, {"AR", "America/Buenos_Aires"}, {"AU", "Australia/Sydney"}, {"BR", "America/Sao_Paulo"}, {"CA", "America/Toronto"}, {"CD", "Africa/Kinshasa"}, {"CL", "America/Santiago"}, {"CN", "Asia/Shanghai"}, {"EC", "America/Guayaquil"}, {"ES", "Europe/Madrid"}, {"GB", "Europe/London"}, {"GL", "America/Godthab"}, {"ID", "Asia/Jakarta"}, {"ML", "Africa/Bamako"}, {"MX", "America/Mexico_City"}, {"MY", "Asia/Kuala_Lumpur"}, {"NZ", "Pacific/Auckland"}, {"PT", "Europe/Lisbon"}, {"RU", "Europe/Moscow"}, {"UA", "Europe/Kiev"}, {"US", "America/New_York"}, {"UZ", "Asia/Tashkent"}, {"PF", "Pacific/Tahiti"}, {"FM", "Pacific/Kosrae"}, {"KI", "Pacific/Tarawa"}, {"KZ", "Asia/Almaty"}, {"MH", "Pacific/Majuro"}, {"MN", "Asia/Ulaanbaatar"}, {"SJ", "Arctic/Longyearbyen"}, {"UM", "Pacific/Midway"}};
        for (n = 0; n < territory_tzid_hack.length; ++n) {
            territory_tzid_hack_map.put(territory_tzid_hack[n][0], territory_tzid_hack[n][1]);
        }
    }
}

