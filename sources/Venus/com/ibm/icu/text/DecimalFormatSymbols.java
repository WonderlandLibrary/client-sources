/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;

public class DecimalFormatSymbols
implements Cloneable,
Serializable {
    public static final int CURRENCY_SPC_CURRENCY_MATCH = 0;
    public static final int CURRENCY_SPC_SURROUNDING_MATCH = 1;
    public static final int CURRENCY_SPC_INSERT = 2;
    private String[] currencySpcBeforeSym;
    private String[] currencySpcAfterSym;
    private static final String[] SYMBOL_KEYS = new String[]{"decimal", "group", "percentSign", "minusSign", "plusSign", "exponential", "perMille", "infinity", "nan", "currencyDecimal", "currencyGroup", "superscriptingExponent"};
    private static final String[] DEF_DIGIT_STRINGS_ARRAY = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final char[] DEF_DIGIT_CHARS_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char DEF_DECIMAL_SEPARATOR = '.';
    private static final char DEF_GROUPING_SEPARATOR = ',';
    private static final char DEF_PERCENT = '%';
    private static final char DEF_MINUS_SIGN = '-';
    private static final char DEF_PLUS_SIGN = '+';
    private static final char DEF_PERMILL = '\u2030';
    private static final String[] SYMBOL_DEFAULTS = new String[]{String.valueOf('.'), String.valueOf(','), String.valueOf('%'), String.valueOf('-'), String.valueOf('+'), "E", String.valueOf('\u2030'), "\u221e", "NaN", null, null, "\u00d7"};
    private static final String LATIN_NUMBERING_SYSTEM = "latn";
    private static final String NUMBER_ELEMENTS = "NumberElements";
    private static final String SYMBOLS = "symbols";
    private char zeroDigit;
    private char[] digits;
    private String[] digitStrings;
    private transient int codePointZero;
    private char groupingSeparator;
    private String groupingSeparatorString;
    private char decimalSeparator;
    private String decimalSeparatorString;
    private char perMill;
    private String perMillString;
    private char percent;
    private String percentString;
    private char digit;
    private char sigDigit;
    private char patternSeparator;
    private String infinity;
    private String NaN;
    private char minusSign;
    private String minusString;
    private char plusSign;
    private String plusString;
    private String currencySymbol;
    private String intlCurrencySymbol;
    private char monetarySeparator;
    private String monetarySeparatorString;
    private char monetaryGroupingSeparator;
    private String monetaryGroupingSeparatorString;
    private char exponential;
    private String exponentSeparator;
    private char padEscape;
    private Locale requestedLocale;
    private ULocale ulocale;
    private String exponentMultiplicationSign = null;
    private static final long serialVersionUID = 5772796243397350300L;
    private static final int currentSerialVersion = 8;
    private int serialVersionOnStream = 8;
    private static final CacheBase<ULocale, CacheData, Void> cachedLocaleData = new SoftCache<ULocale, CacheData, Void>(){

        @Override
        protected CacheData createInstance(ULocale uLocale, Void void_) {
            return DecimalFormatSymbols.access$100(uLocale);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((ULocale)object, (Void)object2);
        }
    };
    private String currencyPattern = null;
    private ULocale validLocale;
    private ULocale actualLocale;
    private transient Currency currency;

    public DecimalFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public DecimalFormatSymbols(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public DecimalFormatSymbols(ULocale uLocale) {
        this.initialize(uLocale, null);
    }

    private DecimalFormatSymbols(Locale locale, NumberingSystem numberingSystem) {
        this(ULocale.forLocale(locale), numberingSystem);
    }

    private DecimalFormatSymbols(ULocale uLocale, NumberingSystem numberingSystem) {
        this.initialize(uLocale, numberingSystem);
    }

    public static DecimalFormatSymbols getInstance() {
        return new DecimalFormatSymbols();
    }

    public static DecimalFormatSymbols getInstance(Locale locale) {
        return new DecimalFormatSymbols(locale);
    }

    public static DecimalFormatSymbols getInstance(ULocale uLocale) {
        return new DecimalFormatSymbols(uLocale);
    }

    public static DecimalFormatSymbols forNumberingSystem(Locale locale, NumberingSystem numberingSystem) {
        return new DecimalFormatSymbols(locale, numberingSystem);
    }

    public static DecimalFormatSymbols forNumberingSystem(ULocale uLocale, NumberingSystem numberingSystem) {
        return new DecimalFormatSymbols(uLocale, numberingSystem);
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public char[] getDigits() {
        return (char[])this.digits.clone();
    }

    public void setZeroDigit(char c) {
        this.zeroDigit = c;
        this.digitStrings = (String[])this.digitStrings.clone();
        this.digits = (char[])this.digits.clone();
        this.digitStrings[0] = String.valueOf(c);
        this.digits[0] = c;
        for (int i = 1; i < 10; ++i) {
            char c2 = (char)(c + i);
            this.digitStrings[i] = String.valueOf(c2);
            this.digits[i] = c2;
        }
        this.codePointZero = c;
    }

    public String[] getDigitStrings() {
        return (String[])this.digitStrings.clone();
    }

    @Deprecated
    public String[] getDigitStringsLocal() {
        return this.digitStrings;
    }

    @Deprecated
    public int getCodePointZero() {
        return this.codePointZero;
    }

    public void setDigitStrings(String[] stringArray) {
        if (stringArray == null) {
            throw new NullPointerException("The input digit string array is null");
        }
        if (stringArray.length != 10) {
            throw new IllegalArgumentException("Number of digit strings is not 10");
        }
        String[] stringArray2 = new String[10];
        char[] cArray = new char[10];
        int n = -1;
        for (int i = 0; i < 10; ++i) {
            int n2;
            int n3;
            String string = stringArray[i];
            if (string == null) {
                throw new IllegalArgumentException("The input digit string array contains a null element");
            }
            stringArray2[i] = string;
            if (string.length() == 0) {
                n3 = -1;
                n2 = 0;
            } else {
                n3 = Character.codePointAt(stringArray[i], 0);
                n2 = Character.charCount(n3);
            }
            if (n2 == string.length()) {
                if (n2 == 1 && cArray != null) {
                    cArray[i] = (char)n3;
                } else {
                    cArray = null;
                }
                if (i == 0) {
                    n = n3;
                    continue;
                }
                if (n3 == n + i) continue;
                n = -1;
                continue;
            }
            n = -1;
            cArray = null;
        }
        this.digitStrings = stringArray2;
        this.codePointZero = n;
        if (cArray == null) {
            this.zeroDigit = DEF_DIGIT_CHARS_ARRAY[0];
            this.digits = DEF_DIGIT_CHARS_ARRAY;
        } else {
            this.zeroDigit = cArray[0];
            this.digits = cArray;
        }
    }

    public char getSignificantDigit() {
        return this.sigDigit;
    }

    public void setSignificantDigit(char c) {
        this.sigDigit = c;
    }

    public char getGroupingSeparator() {
        return this.groupingSeparator;
    }

    public void setGroupingSeparator(char c) {
        this.groupingSeparator = c;
        this.groupingSeparatorString = String.valueOf(c);
    }

    public String getGroupingSeparatorString() {
        return this.groupingSeparatorString;
    }

    public void setGroupingSeparatorString(String string) {
        if (string == null) {
            throw new NullPointerException("The input grouping separator is null");
        }
        this.groupingSeparatorString = string;
        this.groupingSeparator = string.length() == 1 ? string.charAt(0) : (char)44;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public void setDecimalSeparator(char c) {
        this.decimalSeparator = c;
        this.decimalSeparatorString = String.valueOf(c);
    }

    public String getDecimalSeparatorString() {
        return this.decimalSeparatorString;
    }

    public void setDecimalSeparatorString(String string) {
        if (string == null) {
            throw new NullPointerException("The input decimal separator is null");
        }
        this.decimalSeparatorString = string;
        this.decimalSeparator = string.length() == 1 ? string.charAt(0) : (char)46;
    }

    public char getPerMill() {
        return this.perMill;
    }

    public void setPerMill(char c) {
        this.perMill = c;
        this.perMillString = String.valueOf(c);
    }

    public String getPerMillString() {
        return this.perMillString;
    }

    public void setPerMillString(String string) {
        if (string == null) {
            throw new NullPointerException("The input permille string is null");
        }
        this.perMillString = string;
        this.perMill = string.length() == 1 ? string.charAt(0) : (char)8240;
    }

    public char getPercent() {
        return this.percent;
    }

    public void setPercent(char c) {
        this.percent = c;
        this.percentString = String.valueOf(c);
    }

    public String getPercentString() {
        return this.percentString;
    }

    public void setPercentString(String string) {
        if (string == null) {
            throw new NullPointerException("The input percent sign is null");
        }
        this.percentString = string;
        this.percent = string.length() == 1 ? string.charAt(0) : (char)37;
    }

    public char getDigit() {
        return this.digit;
    }

    public void setDigit(char c) {
        this.digit = c;
    }

    public char getPatternSeparator() {
        return this.patternSeparator;
    }

    public void setPatternSeparator(char c) {
        this.patternSeparator = c;
    }

    public String getInfinity() {
        return this.infinity;
    }

    public void setInfinity(String string) {
        this.infinity = string;
    }

    public String getNaN() {
        return this.NaN;
    }

    public void setNaN(String string) {
        this.NaN = string;
    }

    public char getMinusSign() {
        return this.minusSign;
    }

    public void setMinusSign(char c) {
        this.minusSign = c;
        this.minusString = String.valueOf(c);
    }

    public String getMinusSignString() {
        return this.minusString;
    }

    public void setMinusSignString(String string) {
        if (string == null) {
            throw new NullPointerException("The input minus sign is null");
        }
        this.minusString = string;
        this.minusSign = string.length() == 1 ? string.charAt(0) : (char)45;
    }

    public char getPlusSign() {
        return this.plusSign;
    }

    public void setPlusSign(char c) {
        this.plusSign = c;
        this.plusString = String.valueOf(c);
    }

    public String getPlusSignString() {
        return this.plusString;
    }

    public void setPlusSignString(String string) {
        if (string == null) {
            throw new NullPointerException("The input plus sign is null");
        }
        this.plusString = string;
        this.plusSign = string.length() == 1 ? string.charAt(0) : (char)43;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public void setCurrencySymbol(String string) {
        this.currencySymbol = string;
    }

    public String getInternationalCurrencySymbol() {
        return this.intlCurrencySymbol;
    }

    public void setInternationalCurrencySymbol(String string) {
        this.intlCurrencySymbol = string;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        if (currency == null) {
            throw new NullPointerException();
        }
        this.currency = currency;
        this.intlCurrencySymbol = currency.getCurrencyCode();
        this.currencySymbol = currency.getSymbol(this.requestedLocale);
    }

    public char getMonetaryDecimalSeparator() {
        return this.monetarySeparator;
    }

    public void setMonetaryDecimalSeparator(char c) {
        this.monetarySeparator = c;
        this.monetarySeparatorString = String.valueOf(c);
    }

    public String getMonetaryDecimalSeparatorString() {
        return this.monetarySeparatorString;
    }

    public void setMonetaryDecimalSeparatorString(String string) {
        if (string == null) {
            throw new NullPointerException("The input monetary decimal separator is null");
        }
        this.monetarySeparatorString = string;
        this.monetarySeparator = string.length() == 1 ? string.charAt(0) : (char)46;
    }

    public char getMonetaryGroupingSeparator() {
        return this.monetaryGroupingSeparator;
    }

    public void setMonetaryGroupingSeparator(char c) {
        this.monetaryGroupingSeparator = c;
        this.monetaryGroupingSeparatorString = String.valueOf(c);
    }

    public String getMonetaryGroupingSeparatorString() {
        return this.monetaryGroupingSeparatorString;
    }

    public void setMonetaryGroupingSeparatorString(String string) {
        if (string == null) {
            throw new NullPointerException("The input monetary grouping separator is null");
        }
        this.monetaryGroupingSeparatorString = string;
        this.monetaryGroupingSeparator = string.length() == 1 ? string.charAt(0) : (char)44;
    }

    String getCurrencyPattern() {
        return this.currencyPattern;
    }

    public String getExponentMultiplicationSign() {
        return this.exponentMultiplicationSign;
    }

    public void setExponentMultiplicationSign(String string) {
        this.exponentMultiplicationSign = string;
    }

    public String getExponentSeparator() {
        return this.exponentSeparator;
    }

    public void setExponentSeparator(String string) {
        this.exponentSeparator = string;
    }

    public char getPadEscape() {
        return this.padEscape;
    }

    public void setPadEscape(char c) {
        this.padEscape = c;
    }

    public String getPatternForCurrencySpacing(int n, boolean bl) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException("unknown currency spacing: " + n);
        }
        if (bl) {
            return this.currencySpcBeforeSym[n];
        }
        return this.currencySpcAfterSym[n];
    }

    public void setPatternForCurrencySpacing(int n, boolean bl, String string) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException("unknown currency spacing: " + n);
        }
        if (bl) {
            this.currencySpcBeforeSym = (String[])this.currencySpcBeforeSym.clone();
            this.currencySpcBeforeSym[n] = string;
        } else {
            this.currencySpcAfterSym = (String[])this.currencySpcAfterSym.clone();
            this.currencySpcAfterSym[n] = string;
        }
    }

    public Locale getLocale() {
        return this.requestedLocale;
    }

    public ULocale getULocale() {
        return this.ulocale;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        int n;
        if (!(object instanceof DecimalFormatSymbols)) {
            return true;
        }
        if (this == object) {
            return false;
        }
        DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)object;
        for (n = 0; n <= 2; ++n) {
            if (!this.currencySpcBeforeSym[n].equals(decimalFormatSymbols.currencySpcBeforeSym[n])) {
                return true;
            }
            if (this.currencySpcAfterSym[n].equals(decimalFormatSymbols.currencySpcAfterSym[n])) continue;
            return true;
        }
        if (decimalFormatSymbols.digits == null) {
            for (n = 0; n < 10; ++n) {
                if (this.digits[n] == decimalFormatSymbols.zeroDigit + n) continue;
                return true;
            }
        } else if (!Arrays.equals(this.digits, decimalFormatSymbols.digits)) {
            return true;
        }
        return this.groupingSeparator == decimalFormatSymbols.groupingSeparator && this.decimalSeparator == decimalFormatSymbols.decimalSeparator && this.percent == decimalFormatSymbols.percent && this.perMill == decimalFormatSymbols.perMill && this.digit == decimalFormatSymbols.digit && this.minusSign == decimalFormatSymbols.minusSign && this.minusString.equals(decimalFormatSymbols.minusString) && this.patternSeparator == decimalFormatSymbols.patternSeparator && this.infinity.equals(decimalFormatSymbols.infinity) && this.NaN.equals(decimalFormatSymbols.NaN) && this.currencySymbol.equals(decimalFormatSymbols.currencySymbol) && this.intlCurrencySymbol.equals(decimalFormatSymbols.intlCurrencySymbol) && this.padEscape == decimalFormatSymbols.padEscape && this.plusSign == decimalFormatSymbols.plusSign && this.plusString.equals(decimalFormatSymbols.plusString) && this.exponentSeparator.equals(decimalFormatSymbols.exponentSeparator) && this.monetarySeparator == decimalFormatSymbols.monetarySeparator && this.monetaryGroupingSeparator == decimalFormatSymbols.monetaryGroupingSeparator && this.exponentMultiplicationSign.equals(decimalFormatSymbols.exponentMultiplicationSign);
    }

    public int hashCode() {
        int n = this.digits[0];
        n = n * 37 + this.groupingSeparator;
        n = n * 37 + this.decimalSeparator;
        return n;
    }

    private void initialize(ULocale uLocale, NumberingSystem numberingSystem) {
        this.requestedLocale = uLocale.toLocale();
        this.ulocale = uLocale;
        ULocale uLocale2 = numberingSystem == null ? uLocale : uLocale.setKeywordValue("numbers", numberingSystem.getName());
        CacheData cacheData = cachedLocaleData.getInstance(uLocale2, null);
        this.setLocale(cacheData.validLocale, cacheData.validLocale);
        this.setDigitStrings(cacheData.digits);
        String[] stringArray = cacheData.numberElements;
        this.setDecimalSeparatorString(stringArray[0]);
        this.setGroupingSeparatorString(stringArray[5]);
        this.patternSeparator = (char)59;
        this.setPercentString(stringArray[5]);
        this.setMinusSignString(stringArray[5]);
        this.setPlusSignString(stringArray[5]);
        this.setExponentSeparator(stringArray[5]);
        this.setPerMillString(stringArray[5]);
        this.setInfinity(stringArray[7]);
        this.setNaN(stringArray[8]);
        this.setMonetaryDecimalSeparatorString(stringArray[9]);
        this.setMonetaryGroupingSeparatorString(stringArray[10]);
        this.setExponentMultiplicationSign(stringArray[11]);
        this.digit = (char)35;
        this.padEscape = (char)42;
        this.sigDigit = (char)64;
        CurrencyData.CurrencyDisplayInfo currencyDisplayInfo = CurrencyData.provider.getInstance(uLocale, true);
        this.currency = Currency.getInstance(uLocale);
        if (this.currency != null) {
            this.intlCurrencySymbol = this.currency.getCurrencyCode();
            this.currencySymbol = this.currency.getName(uLocale, 0, null);
            CurrencyData.CurrencyFormatInfo currencyFormatInfo = currencyDisplayInfo.getFormatInfo(this.intlCurrencySymbol);
            if (currencyFormatInfo != null) {
                this.currencyPattern = currencyFormatInfo.currencyPattern;
                this.setMonetaryDecimalSeparatorString(currencyFormatInfo.monetaryDecimalSeparator);
                this.setMonetaryGroupingSeparatorString(currencyFormatInfo.monetaryGroupingSeparator);
            }
        } else {
            this.intlCurrencySymbol = "XXX";
            this.currencySymbol = "\u00a4";
        }
        this.initSpacingInfo(currencyDisplayInfo.getSpacingInfo());
    }

    private static CacheData loadData(ULocale uLocale) {
        String string;
        Object object;
        NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
        String[] stringArray = new String[10];
        if (numberingSystem != null && numberingSystem.getRadix() == 10 && !numberingSystem.isAlgorithmic() && NumberingSystem.isValidDigitString(numberingSystem.getDescription())) {
            object = numberingSystem.getDescription();
            int n = 0;
            for (int i = 0; i < 10; ++i) {
                int n2 = ((String)object).codePointAt(n);
                int n3 = n + Character.charCount(n2);
                stringArray[i] = ((String)object).substring(n, n3);
                n = n3;
            }
            string = numberingSystem.getName();
        } else {
            stringArray = DEF_DIGIT_STRINGS_ARRAY;
            string = LATIN_NUMBERING_SYSTEM;
        }
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        ULocale uLocale2 = ((ICUResourceBundle)object).getULocale();
        String[] stringArray2 = new String[SYMBOL_KEYS.length];
        DecFmtDataSink decFmtDataSink = new DecFmtDataSink(stringArray2);
        try {
            ((ICUResourceBundle)object).getAllItemsWithFallback("NumberElements/" + string + "/" + SYMBOLS, decFmtDataSink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        boolean bl = false;
        for (String string2 : stringArray2) {
            if (string2 != null) continue;
            bl = true;
            break;
        }
        if (bl && !string.equals(LATIN_NUMBERING_SYSTEM)) {
            ((ICUResourceBundle)object).getAllItemsWithFallback("NumberElements/latn/symbols", decFmtDataSink);
        }
        for (int i = 0; i < SYMBOL_KEYS.length; ++i) {
            if (stringArray2[i] != null) continue;
            stringArray2[i] = SYMBOL_DEFAULTS[i];
        }
        if (stringArray2[0] == null) {
            stringArray2[9] = stringArray2[9];
        }
        if (stringArray2[5] == null) {
            stringArray2[10] = stringArray2[8];
        }
        return new CacheData(uLocale2, stringArray, stringArray2);
    }

    private void initSpacingInfo(CurrencyData.CurrencySpacingInfo currencySpacingInfo) {
        this.currencySpcBeforeSym = currencySpacingInfo.getBeforeSymbols();
        this.currencySpcAfterSym = currencySpacingInfo.getAfterSymbols();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.monetarySeparator = this.decimalSeparator;
            this.exponential = (char)69;
        }
        if (this.serialVersionOnStream < 2) {
            this.padEscape = (char)42;
            this.plusSign = (char)43;
            this.exponentSeparator = String.valueOf(this.exponential);
        }
        if (this.serialVersionOnStream < 3) {
            this.requestedLocale = Locale.getDefault();
        }
        if (this.serialVersionOnStream < 4) {
            this.ulocale = ULocale.forLocale(this.requestedLocale);
        }
        if (this.serialVersionOnStream < 5) {
            this.monetaryGroupingSeparator = this.groupingSeparator;
        }
        if (this.serialVersionOnStream < 6) {
            if (this.currencySpcBeforeSym == null) {
                this.currencySpcBeforeSym = new String[3];
            }
            if (this.currencySpcAfterSym == null) {
                this.currencySpcAfterSym = new String[3];
            }
            this.initSpacingInfo(CurrencyData.CurrencySpacingInfo.DEFAULT);
        }
        if (this.serialVersionOnStream < 7) {
            if (this.minusString == null) {
                this.minusString = String.valueOf(this.minusSign);
            }
            if (this.plusString == null) {
                this.plusString = String.valueOf(this.plusSign);
            }
        }
        if (this.serialVersionOnStream < 8 && this.exponentMultiplicationSign == null) {
            this.exponentMultiplicationSign = "\u00d7";
        }
        if (this.serialVersionOnStream < 9) {
            if (this.digitStrings == null) {
                this.digitStrings = new String[10];
                if (this.digits != null && this.digits.length == 10) {
                    this.zeroDigit = this.digits[0];
                    for (int i = 0; i < 10; ++i) {
                        this.digitStrings[i] = String.valueOf(this.digits[i]);
                    }
                } else {
                    char c = this.zeroDigit;
                    if (this.digits == null) {
                        this.digits = new char[10];
                    }
                    for (int i = 0; i < 10; ++i) {
                        this.digits[i] = c;
                        this.digitStrings[i] = String.valueOf(c);
                        c = (char)(c + '\u0001');
                    }
                }
            }
            if (this.decimalSeparatorString == null) {
                this.decimalSeparatorString = String.valueOf(this.decimalSeparator);
            }
            if (this.groupingSeparatorString == null) {
                this.groupingSeparatorString = String.valueOf(this.groupingSeparator);
            }
            if (this.percentString == null) {
                this.percentString = String.valueOf(this.percent);
            }
            if (this.perMillString == null) {
                this.perMillString = String.valueOf(this.perMill);
            }
            if (this.monetarySeparatorString == null) {
                this.monetarySeparatorString = String.valueOf(this.monetarySeparator);
            }
            if (this.monetaryGroupingSeparatorString == null) {
                this.monetaryGroupingSeparatorString = String.valueOf(this.monetaryGroupingSeparator);
            }
        }
        this.serialVersionOnStream = 8;
        this.currency = Currency.getInstance(this.intlCurrencySymbol);
        this.setDigitStrings(this.digitStrings);
    }

    public final ULocale getLocale(ULocale.Type type) {
        return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        if (uLocale == null != (uLocale2 == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = uLocale;
        this.actualLocale = uLocale2;
    }

    static String[] access$000() {
        return SYMBOL_KEYS;
    }

    static CacheData access$100(ULocale uLocale) {
        return DecimalFormatSymbols.loadData(uLocale);
    }

    private static class CacheData {
        final ULocale validLocale;
        final String[] digits;
        final String[] numberElements;

        public CacheData(ULocale uLocale, String[] stringArray, String[] stringArray2) {
            this.validLocale = uLocale;
            this.digits = stringArray;
            this.numberElements = stringArray2;
        }
    }

    private static final class DecFmtDataSink
    extends UResource.Sink {
        private String[] numberElements;

        public DecFmtDataSink(String[] stringArray) {
            this.numberElements = stringArray;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                for (int i = 0; i < DecimalFormatSymbols.access$000().length; ++i) {
                    if (!key.contentEquals(DecimalFormatSymbols.access$000()[i])) continue;
                    if (this.numberElements[i] != null) break;
                    this.numberElements[i] = value.toString();
                    break;
                }
                ++n;
            }
        }
    }
}

