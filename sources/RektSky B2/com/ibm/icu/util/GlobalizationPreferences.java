package com.ibm.icu.util;

import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;
import java.util.*;

public class GlobalizationPreferences implements Freezable<GlobalizationPreferences>
{
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
    
    public GlobalizationPreferences setLocales(final List<ULocale> inputLocales) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = this.processLocales(inputLocales);
        return this;
    }
    
    public List<ULocale> getLocales() {
        List<ULocale> result;
        if (this.locales == null) {
            result = this.guessLocales();
        }
        else {
            result = new ArrayList<ULocale>();
            result.addAll(this.locales);
        }
        return result;
    }
    
    public ULocale getLocale(final int index) {
        List<ULocale> lcls = this.locales;
        if (lcls == null) {
            lcls = this.guessLocales();
        }
        if (index >= 0 && index < lcls.size()) {
            return lcls.get(index);
        }
        return null;
    }
    
    public GlobalizationPreferences setLocales(final ULocale[] uLocales) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return this.setLocales(Arrays.asList(uLocales));
    }
    
    public GlobalizationPreferences setLocale(final ULocale uLocale) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return this.setLocales(new ULocale[] { uLocale });
    }
    
    public GlobalizationPreferences setLocales(final String acceptLanguageString) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        ULocale[] acceptLocales = null;
        try {
            acceptLocales = ULocale.parseAcceptLanguage(acceptLanguageString, true);
        }
        catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid Accept-Language string");
        }
        return this.setLocales(acceptLocales);
    }
    
    public ResourceBundle getResourceBundle(final String baseName) {
        return this.getResourceBundle(baseName, null);
    }
    
    public ResourceBundle getResourceBundle(final String baseName, final ClassLoader loader) {
        UResourceBundle urb = null;
        UResourceBundle candidate = null;
        String actualLocaleName = null;
        final List<ULocale> fallbacks = this.getLocales();
        for (int i = 0; i < fallbacks.size(); ++i) {
            final String localeName = fallbacks.get(i).toString();
            if (actualLocaleName != null && localeName.equals(actualLocaleName)) {
                urb = candidate;
                break;
            }
            try {
                if (loader == null) {
                    candidate = UResourceBundle.getBundleInstance(baseName, localeName);
                }
                else {
                    candidate = UResourceBundle.getBundleInstance(baseName, localeName, loader);
                }
                if (candidate != null) {
                    actualLocaleName = candidate.getULocale().getName();
                    if (actualLocaleName.equals(localeName)) {
                        urb = candidate;
                        break;
                    }
                    if (urb == null) {
                        urb = candidate;
                    }
                }
            }
            catch (MissingResourceException mre) {
                actualLocaleName = null;
            }
        }
        if (urb == null) {
            throw new MissingResourceException("Can't find bundle for base name " + baseName, baseName, "");
        }
        return urb;
    }
    
    public GlobalizationPreferences setTerritory(final String territory) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.territory = territory;
        return this;
    }
    
    public String getTerritory() {
        if (this.territory == null) {
            return this.guessTerritory();
        }
        return this.territory;
    }
    
    public GlobalizationPreferences setCurrency(final Currency currency) {
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
    
    public GlobalizationPreferences setCalendar(final Calendar calendar) {
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
        final Calendar temp = (Calendar)this.calendar.clone();
        temp.setTimeZone(this.getTimeZone());
        temp.setTimeInMillis(System.currentTimeMillis());
        return temp;
    }
    
    public GlobalizationPreferences setTimeZone(final TimeZone timezone) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.timezone = (TimeZone)timezone.clone();
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
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", e);
        }
    }
    
    public GlobalizationPreferences setCollator(final Collator collator) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        try {
            this.collator = (Collator)collator.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", e);
        }
        return this;
    }
    
    public BreakIterator getBreakIterator(final int type) {
        if (type < 0 || type >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        if (this.breakIterators == null || this.breakIterators[type] == null) {
            return this.guessBreakIterator(type);
        }
        return (BreakIterator)this.breakIterators[type].clone();
    }
    
    public GlobalizationPreferences setBreakIterator(final int type, final BreakIterator iterator) {
        if (type < 0 || type >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.breakIterators == null) {
            this.breakIterators = new BreakIterator[5];
        }
        this.breakIterators[type] = (BreakIterator)iterator.clone();
        return this;
    }
    
    public String getDisplayName(final String id, final int type) {
        String result = id;
        for (final ULocale locale : this.getLocales()) {
            if (!this.isAvailableLocale(locale, 0)) {
                continue;
            }
            switch (type) {
                case 0: {
                    result = ULocale.getDisplayName(id, locale);
                    break;
                }
                case 1: {
                    result = ULocale.getDisplayLanguage(id, locale);
                    break;
                }
                case 2: {
                    result = ULocale.getDisplayScript("und-" + id, locale);
                    break;
                }
                case 3: {
                    result = ULocale.getDisplayCountry("und-" + id, locale);
                    break;
                }
                case 4: {
                    result = ULocale.getDisplayVariant("und-QQ-" + id, locale);
                    break;
                }
                case 5: {
                    result = ULocale.getDisplayKeyword(id, locale);
                    break;
                }
                case 6: {
                    final String[] parts = new String[2];
                    Utility.split(id, '=', parts);
                    result = ULocale.getDisplayKeywordValue("und@" + id, parts[0], locale);
                    if (result.equals(parts[1])) {
                        continue;
                    }
                    break;
                }
                case 7:
                case 8: {
                    final Currency temp = new Currency(id);
                    result = temp.getName(locale, (type == 7) ? 1 : 0, new boolean[1]);
                    break;
                }
                case 9: {
                    final SimpleDateFormat dtf = new SimpleDateFormat("vvvv", locale);
                    dtf.setTimeZone(TimeZone.getFrozenTimeZone(id));
                    result = dtf.format(new Date());
                    boolean isBadStr = false;
                    String teststr = result;
                    final int sidx = result.indexOf(40);
                    final int eidx = result.indexOf(41);
                    if (sidx != -1 && eidx != -1 && eidx - sidx == 3) {
                        teststr = result.substring(sidx + 1, eidx);
                    }
                    if (teststr.length() == 2) {
                        isBadStr = true;
                        for (int i = 0; i < 2; ++i) {
                            final char c = teststr.charAt(i);
                            if (c < 'A' || 'Z' < c) {
                                isBadStr = false;
                                break;
                            }
                        }
                    }
                    if (isBadStr) {
                        continue;
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown type: " + type);
                }
            }
            if (!id.equals(result)) {
                return result;
            }
        }
        return result;
    }
    
    public GlobalizationPreferences setDateFormat(final int dateStyle, final int timeStyle, final DateFormat format) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.dateFormats == null) {
            this.dateFormats = new DateFormat[5][5];
        }
        this.dateFormats[dateStyle][timeStyle] = (DateFormat)format.clone();
        return this;
    }
    
    public DateFormat getDateFormat(final int dateStyle, final int timeStyle) {
        if ((dateStyle == 4 && timeStyle == 4) || dateStyle < 0 || dateStyle >= 5 || timeStyle < 0 || timeStyle >= 5) {
            throw new IllegalArgumentException("Illegal date format style arguments");
        }
        DateFormat result = null;
        if (this.dateFormats != null) {
            result = this.dateFormats[dateStyle][timeStyle];
        }
        if (result != null) {
            result = (DateFormat)result.clone();
            result.setTimeZone(this.getTimeZone());
        }
        else {
            result = this.guessDateFormat(dateStyle, timeStyle);
        }
        return result;
    }
    
    public NumberFormat getNumberFormat(final int style) {
        if (style < 0 || style >= 5) {
            throw new IllegalArgumentException("Illegal number format type");
        }
        NumberFormat result = null;
        if (this.numberFormats != null) {
            result = this.numberFormats[style];
        }
        if (result != null) {
            result = (NumberFormat)result.clone();
        }
        else {
            result = this.guessNumberFormat(style);
        }
        return result;
    }
    
    public GlobalizationPreferences setNumberFormat(final int style, final NumberFormat format) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.numberFormats == null) {
            this.numberFormats = new NumberFormat[5];
        }
        this.numberFormats[style] = (NumberFormat)format.clone();
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
    
    protected List<ULocale> processLocales(final List<ULocale> inputLocales) {
        final List<ULocale> result = new ArrayList<ULocale>();
        for (int i = 0; i < inputLocales.size(); ++i) {
            final ULocale uloc = inputLocales.get(i);
            final String language = uloc.getLanguage();
            final String script = uloc.getScript();
            final String country = uloc.getCountry();
            final String variant = uloc.getVariant();
            boolean bInserted = false;
            for (int j = 0; j < result.size(); ++j) {
                final ULocale u = result.get(j);
                if (u.getLanguage().equals(language)) {
                    final String s = u.getScript();
                    final String c = u.getCountry();
                    final String v = u.getVariant();
                    if (!s.equals(script)) {
                        if (s.length() == 0 && c.length() == 0 && v.length() == 0) {
                            result.add(j, uloc);
                            bInserted = true;
                            break;
                        }
                        if (s.length() == 0 && c.equals(country)) {
                            result.add(j, uloc);
                            bInserted = true;
                            break;
                        }
                        if (script.length() == 0 && country.length() > 0 && c.length() == 0) {
                            result.add(j, uloc);
                            bInserted = true;
                            break;
                        }
                    }
                    else {
                        if (!c.equals(country) && c.length() == 0 && v.length() == 0) {
                            result.add(j, uloc);
                            bInserted = true;
                            break;
                        }
                        if (!v.equals(variant) && v.length() == 0) {
                            result.add(j, uloc);
                            bInserted = true;
                            break;
                        }
                    }
                }
            }
            if (!bInserted) {
                result.add(uloc);
            }
        }
        for (int index = 0; index < result.size(); ++index) {
            ULocale uloc = result.get(index);
            while ((uloc = uloc.getFallback()) != null && uloc.getLanguage().length() != 0) {
                ++index;
                result.add(index, uloc);
            }
        }
        for (int index = 0; index < result.size() - 1; ++index) {
            final ULocale uloc = result.get(index);
            boolean bRemoved = false;
            for (int k = index + 1; k < result.size(); ++k) {
                if (uloc.equals(result.get(k))) {
                    result.remove(index);
                    bRemoved = true;
                    break;
                }
            }
            if (!bRemoved) {}
        }
        return result;
    }
    
    protected DateFormat guessDateFormat(final int dateStyle, final int timeStyle) {
        ULocale dfLocale = this.getAvailableLocale(2);
        if (dfLocale == null) {
            dfLocale = ULocale.ROOT;
        }
        DateFormat result;
        if (timeStyle == 4) {
            result = DateFormat.getDateInstance(this.getCalendar(), dateStyle, dfLocale);
        }
        else if (dateStyle == 4) {
            result = DateFormat.getTimeInstance(this.getCalendar(), timeStyle, dfLocale);
        }
        else {
            result = DateFormat.getDateTimeInstance(this.getCalendar(), dateStyle, timeStyle, dfLocale);
        }
        return result;
    }
    
    protected NumberFormat guessNumberFormat(final int style) {
        ULocale nfLocale = this.getAvailableLocale(3);
        if (nfLocale == null) {
            nfLocale = ULocale.ROOT;
        }
        NumberFormat result = null;
        switch (style) {
            case 0: {
                result = NumberFormat.getInstance(nfLocale);
                break;
            }
            case 3: {
                result = NumberFormat.getScientificInstance(nfLocale);
                break;
            }
            case 4: {
                result = NumberFormat.getIntegerInstance(nfLocale);
                break;
            }
            case 2: {
                result = NumberFormat.getPercentInstance(nfLocale);
                break;
            }
            case 1: {
                result = NumberFormat.getCurrencyInstance(nfLocale);
                result.setCurrency(this.getCurrency());
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown number format style");
            }
        }
        return result;
    }
    
    protected String guessTerritory() {
        for (final ULocale locale : this.getLocales()) {
            final String result = locale.getCountry();
            if (result.length() != 0) {
                return result;
            }
        }
        final ULocale firstLocale = this.getLocale(0);
        final String language = firstLocale.getLanguage();
        final String script = firstLocale.getScript();
        String result = null;
        if (script.length() != 0) {
            result = GlobalizationPreferences.language_territory_hack_map.get(language + "_" + script);
        }
        if (result == null) {
            result = GlobalizationPreferences.language_territory_hack_map.get(language);
        }
        if (result == null) {
            result = "US";
        }
        return result;
    }
    
    protected Currency guessCurrency() {
        return Currency.getInstance(new ULocale("und-" + this.getTerritory()));
    }
    
    protected List<ULocale> guessLocales() {
        if (this.implicitLocales == null) {
            final List<ULocale> result = new ArrayList<ULocale>(1);
            result.add(ULocale.getDefault());
            this.implicitLocales = this.processLocales(result);
        }
        return this.implicitLocales;
    }
    
    protected Collator guessCollator() {
        ULocale collLocale = this.getAvailableLocale(4);
        if (collLocale == null) {
            collLocale = ULocale.ROOT;
        }
        return Collator.getInstance(collLocale);
    }
    
    protected BreakIterator guessBreakIterator(final int type) {
        BreakIterator bitr = null;
        ULocale brkLocale = this.getAvailableLocale(5);
        if (brkLocale == null) {
            brkLocale = ULocale.ROOT;
        }
        switch (type) {
            case 0: {
                bitr = BreakIterator.getCharacterInstance(brkLocale);
                break;
            }
            case 4: {
                bitr = BreakIterator.getTitleInstance(brkLocale);
                break;
            }
            case 1: {
                bitr = BreakIterator.getWordInstance(brkLocale);
                break;
            }
            case 2: {
                bitr = BreakIterator.getLineInstance(brkLocale);
                break;
            }
            case 3: {
                bitr = BreakIterator.getSentenceInstance(brkLocale);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown break iterator type");
            }
        }
        return bitr;
    }
    
    protected TimeZone guessTimeZone() {
        String timezoneString = GlobalizationPreferences.territory_tzid_hack_map.get(this.getTerritory());
        if (timezoneString == null) {
            final String[] attempt = TimeZone.getAvailableIDs(this.getTerritory());
            if (attempt.length == 0) {
                timezoneString = "Etc/GMT";
            }
            else {
                int i;
                for (i = 0; i < attempt.length && attempt[i].indexOf("/") < 0; ++i) {}
                if (i > attempt.length) {
                    i = 0;
                }
                timezoneString = attempt[i];
            }
        }
        return TimeZone.getTimeZone(timezoneString);
    }
    
    protected Calendar guessCalendar() {
        ULocale calLocale = this.getAvailableLocale(1);
        if (calLocale == null) {
            calLocale = ULocale.US;
        }
        return Calendar.getInstance(this.getTimeZone(), calLocale);
    }
    
    private ULocale getAvailableLocale(final int type) {
        final List<ULocale> locs = this.getLocales();
        ULocale result = null;
        for (int i = 0; i < locs.size(); ++i) {
            final ULocale l = locs.get(i);
            if (this.isAvailableLocale(l, type)) {
                result = l;
                break;
            }
        }
        return result;
    }
    
    private boolean isAvailableLocale(final ULocale loc, final int type) {
        final BitSet bits = GlobalizationPreferences.available_locales.get(loc);
        return bits != null && bits.get(type);
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
            final GlobalizationPreferences result = (GlobalizationPreferences)this.clone();
            result.frozen = false;
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    static {
        available_locales = new HashMap<ULocale, BitSet>();
        final ULocale[] allLocales = ULocale.getAvailableLocales();
        for (int i = 0; i < allLocales.length; ++i) {
            final BitSet bits = new BitSet(6);
            GlobalizationPreferences.available_locales.put(allLocales[i], bits);
            bits.set(0);
        }
        final ULocale[] calLocales = Calendar.getAvailableULocales();
        for (int j = 0; j < calLocales.length; ++j) {
            BitSet bits = GlobalizationPreferences.available_locales.get(calLocales[j]);
            if (bits == null) {
                bits = new BitSet(6);
                GlobalizationPreferences.available_locales.put(allLocales[j], bits);
            }
            bits.set(1);
        }
        final ULocale[] dateLocales = DateFormat.getAvailableULocales();
        for (int k = 0; k < dateLocales.length; ++k) {
            BitSet bits = GlobalizationPreferences.available_locales.get(dateLocales[k]);
            if (bits == null) {
                bits = new BitSet(6);
                GlobalizationPreferences.available_locales.put(allLocales[k], bits);
            }
            bits.set(2);
        }
        final ULocale[] numLocales = NumberFormat.getAvailableULocales();
        for (int l = 0; l < numLocales.length; ++l) {
            BitSet bits = GlobalizationPreferences.available_locales.get(numLocales[l]);
            if (bits == null) {
                bits = new BitSet(6);
                GlobalizationPreferences.available_locales.put(allLocales[l], bits);
            }
            bits.set(3);
        }
        final ULocale[] collLocales = Collator.getAvailableULocales();
        for (int m = 0; m < collLocales.length; ++m) {
            BitSet bits = GlobalizationPreferences.available_locales.get(collLocales[m]);
            if (bits == null) {
                bits = new BitSet(6);
                GlobalizationPreferences.available_locales.put(allLocales[m], bits);
            }
            bits.set(4);
        }
        final ULocale[] brkLocales = BreakIterator.getAvailableULocales();
        for (int i2 = 0; i2 < brkLocales.length; ++i2) {
            final BitSet bits = GlobalizationPreferences.available_locales.get(brkLocales[i2]);
            bits.set(5);
        }
        language_territory_hack_map = new HashMap<String, String>();
        language_territory_hack = new String[][] { { "af", "ZA" }, { "am", "ET" }, { "ar", "SA" }, { "as", "IN" }, { "ay", "PE" }, { "az", "AZ" }, { "bal", "PK" }, { "be", "BY" }, { "bg", "BG" }, { "bn", "IN" }, { "bs", "BA" }, { "ca", "ES" }, { "ch", "MP" }, { "cpe", "SL" }, { "cs", "CZ" }, { "cy", "GB" }, { "da", "DK" }, { "de", "DE" }, { "dv", "MV" }, { "dz", "BT" }, { "el", "GR" }, { "en", "US" }, { "es", "ES" }, { "et", "EE" }, { "eu", "ES" }, { "fa", "IR" }, { "fi", "FI" }, { "fil", "PH" }, { "fj", "FJ" }, { "fo", "FO" }, { "fr", "FR" }, { "ga", "IE" }, { "gd", "GB" }, { "gl", "ES" }, { "gn", "PY" }, { "gu", "IN" }, { "gv", "GB" }, { "ha", "NG" }, { "he", "IL" }, { "hi", "IN" }, { "ho", "PG" }, { "hr", "HR" }, { "ht", "HT" }, { "hu", "HU" }, { "hy", "AM" }, { "id", "ID" }, { "is", "IS" }, { "it", "IT" }, { "ja", "JP" }, { "ka", "GE" }, { "kk", "KZ" }, { "kl", "GL" }, { "km", "KH" }, { "kn", "IN" }, { "ko", "KR" }, { "kok", "IN" }, { "ks", "IN" }, { "ku", "TR" }, { "ky", "KG" }, { "la", "VA" }, { "lb", "LU" }, { "ln", "CG" }, { "lo", "LA" }, { "lt", "LT" }, { "lv", "LV" }, { "mai", "IN" }, { "men", "GN" }, { "mg", "MG" }, { "mh", "MH" }, { "mk", "MK" }, { "ml", "IN" }, { "mn", "MN" }, { "mni", "IN" }, { "mo", "MD" }, { "mr", "IN" }, { "ms", "MY" }, { "mt", "MT" }, { "my", "MM" }, { "na", "NR" }, { "nb", "NO" }, { "nd", "ZA" }, { "ne", "NP" }, { "niu", "NU" }, { "nl", "NL" }, { "nn", "NO" }, { "no", "NO" }, { "nr", "ZA" }, { "nso", "ZA" }, { "ny", "MW" }, { "om", "KE" }, { "or", "IN" }, { "pa", "IN" }, { "pau", "PW" }, { "pl", "PL" }, { "ps", "PK" }, { "pt", "BR" }, { "qu", "PE" }, { "rn", "BI" }, { "ro", "RO" }, { "ru", "RU" }, { "rw", "RW" }, { "sd", "IN" }, { "sg", "CF" }, { "si", "LK" }, { "sk", "SK" }, { "sl", "SI" }, { "sm", "WS" }, { "so", "DJ" }, { "sq", "CS" }, { "sr", "CS" }, { "ss", "ZA" }, { "st", "ZA" }, { "sv", "SE" }, { "sw", "KE" }, { "ta", "IN" }, { "te", "IN" }, { "tem", "SL" }, { "tet", "TL" }, { "th", "TH" }, { "ti", "ET" }, { "tg", "TJ" }, { "tk", "TM" }, { "tkl", "TK" }, { "tvl", "TV" }, { "tl", "PH" }, { "tn", "ZA" }, { "to", "TO" }, { "tpi", "PG" }, { "tr", "TR" }, { "ts", "ZA" }, { "uk", "UA" }, { "ur", "IN" }, { "uz", "UZ" }, { "ve", "ZA" }, { "vi", "VN" }, { "wo", "SN" }, { "xh", "ZA" }, { "zh", "CN" }, { "zh_Hant", "TW" }, { "zu", "ZA" }, { "aa", "ET" }, { "byn", "ER" }, { "eo", "DE" }, { "gez", "ET" }, { "haw", "US" }, { "iu", "CA" }, { "kw", "GB" }, { "sa", "IN" }, { "sh", "HR" }, { "sid", "ET" }, { "syr", "SY" }, { "tig", "ER" }, { "tt", "RU" }, { "wal", "ET" } };
        for (int i3 = 0; i3 < GlobalizationPreferences.language_territory_hack.length; ++i3) {
            GlobalizationPreferences.language_territory_hack_map.put(GlobalizationPreferences.language_territory_hack[i3][0], GlobalizationPreferences.language_territory_hack[i3][1]);
        }
        territory_tzid_hack_map = new HashMap<String, String>();
        territory_tzid_hack = new String[][] { { "AQ", "Antarctica/McMurdo" }, { "AR", "America/Buenos_Aires" }, { "AU", "Australia/Sydney" }, { "BR", "America/Sao_Paulo" }, { "CA", "America/Toronto" }, { "CD", "Africa/Kinshasa" }, { "CL", "America/Santiago" }, { "CN", "Asia/Shanghai" }, { "EC", "America/Guayaquil" }, { "ES", "Europe/Madrid" }, { "GB", "Europe/London" }, { "GL", "America/Godthab" }, { "ID", "Asia/Jakarta" }, { "ML", "Africa/Bamako" }, { "MX", "America/Mexico_City" }, { "MY", "Asia/Kuala_Lumpur" }, { "NZ", "Pacific/Auckland" }, { "PT", "Europe/Lisbon" }, { "RU", "Europe/Moscow" }, { "UA", "Europe/Kiev" }, { "US", "America/New_York" }, { "UZ", "Asia/Tashkent" }, { "PF", "Pacific/Tahiti" }, { "FM", "Pacific/Kosrae" }, { "KI", "Pacific/Tarawa" }, { "KZ", "Asia/Almaty" }, { "MH", "Pacific/Majuro" }, { "MN", "Asia/Ulaanbaatar" }, { "SJ", "Arctic/Longyearbyen" }, { "UM", "Pacific/Midway" } };
        for (int i3 = 0; i3 < GlobalizationPreferences.territory_tzid_hack.length; ++i3) {
            GlobalizationPreferences.territory_tzid_hack_map.put(GlobalizationPreferences.territory_tzid_hack[i3][0], GlobalizationPreferences.territory_tzid_hack[i3][1]);
        }
    }
}
