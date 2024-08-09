/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.MissingResourceException;

public final class DateNumberFormat
extends NumberFormat {
    private static final long serialVersionUID = -6315692826916346953L;
    private char[] digits;
    private char zeroDigit;
    private char minusSign;
    private boolean positiveOnly = false;
    private static final int DECIMAL_BUF_SIZE = 20;
    private transient char[] decimalBuf = new char[20];
    private static SimpleCache<ULocale, char[]> CACHE = new SimpleCache();
    private int maxIntDigits;
    private int minIntDigits;
    private static final long PARSE_THRESHOLD = 0xCCCCCCCCCCCCCCBL;

    public DateNumberFormat(ULocale uLocale, String string, String string2) {
        if (string.length() > 10) {
            throw new UnsupportedOperationException("DateNumberFormat does not support digits out of BMP.");
        }
        this.initialize(uLocale, string, string2);
    }

    public DateNumberFormat(ULocale uLocale, char c, String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; ++i) {
            stringBuffer.append((char)(c + i));
        }
        this.initialize(uLocale, stringBuffer.toString(), string);
    }

    private void initialize(ULocale uLocale, String string, String string2) {
        char[] cArray = CACHE.get(uLocale);
        if (cArray == null) {
            String string3;
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            try {
                string3 = iCUResourceBundle.getStringWithFallback("NumberElements/" + string2 + "/symbols/minusSign");
            } catch (MissingResourceException missingResourceException) {
                if (!string2.equals("latn")) {
                    try {
                        string3 = iCUResourceBundle.getStringWithFallback("NumberElements/latn/symbols/minusSign");
                    } catch (MissingResourceException missingResourceException2) {
                        string3 = "-";
                    }
                }
                string3 = "-";
            }
            cArray = new char[11];
            for (int i = 0; i < 10; ++i) {
                cArray[i] = string.charAt(i);
            }
            cArray[10] = string3.charAt(0);
            CACHE.put(uLocale, cArray);
        }
        this.digits = new char[10];
        System.arraycopy(cArray, 0, this.digits, 0, 10);
        this.zeroDigit = this.digits[0];
        this.minusSign = cArray[10];
    }

    @Override
    public void setMaximumIntegerDigits(int n) {
        this.maxIntDigits = n;
    }

    @Override
    public int getMaximumIntegerDigits() {
        return this.maxIntDigits;
    }

    @Override
    public void setMinimumIntegerDigits(int n) {
        this.minIntDigits = n;
    }

    @Override
    public int getMinimumIntegerDigits() {
        return this.minIntDigits;
    }

    public void setParsePositiveOnly(boolean bl) {
        this.positiveOnly = bl;
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public void setZeroDigit(char c) {
        this.zeroDigit = c;
        if (this.digits == null) {
            this.digits = new char[10];
        }
        this.digits[0] = c;
        for (int i = 1; i < 10; ++i) {
            this.digits[i] = (char)(c + i);
        }
    }

    public char[] getDigits() {
        return (char[])this.digits.clone();
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(double, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (l < 0L) {
            stringBuffer.append(this.minusSign);
            l = -l;
        }
        int n = (int)l;
        int n2 = this.decimalBuf.length < this.maxIntDigits ? this.decimalBuf.length : this.maxIntDigits;
        int n3 = n2 - 1;
        while (true) {
            this.decimalBuf[n3] = this.digits[n % 10];
            if (n3 == 0 || (n /= 10) == 0) break;
            --n3;
        }
        for (int i = this.minIntDigits - (n2 - n3); i > 0; --i) {
            this.decimalBuf[--n3] = this.digits[0];
        }
        int n4 = n2 - n3;
        stringBuffer.append(this.decimalBuf, n3, n4);
        fieldPosition.setBeginIndex(0);
        if (fieldPosition.getField() == 0) {
            fieldPosition.setEndIndex(n4);
        } else {
            fieldPosition.setEndIndex(0);
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigInteger, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(com.ibm.icu.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        long l = 0L;
        boolean bl = false;
        boolean bl2 = false;
        int n = parsePosition.getIndex();
        int n2 = 0;
        while (n + n2 < string.length()) {
            char c = string.charAt(n + n2);
            if (n2 == 0 && c == this.minusSign) {
                if (this.positiveOnly) break;
                bl2 = true;
            } else {
                int n3 = c - this.digits[0];
                if (n3 < 0 || 9 < n3) {
                    n3 = UCharacter.digit(c);
                }
                if (n3 < 0 || 9 < n3) {
                    for (n3 = 0; n3 < 10 && c != this.digits[n3]; ++n3) {
                    }
                }
                if (0 > n3 || n3 > 9 || l >= 0xCCCCCCCCCCCCCCBL) break;
                bl = true;
                l = l * 10L + (long)n3;
            }
            ++n2;
        }
        Long l2 = null;
        if (bl) {
            l = bl2 ? l * -1L : l;
            l2 = l;
            parsePosition.setIndex(n + n2);
        }
        return l2;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !super.equals(object) || !(object instanceof DateNumberFormat)) {
            return true;
        }
        DateNumberFormat dateNumberFormat = (DateNumberFormat)object;
        return this.maxIntDigits == dateNumberFormat.maxIntDigits && this.minIntDigits == dateNumberFormat.minIntDigits && this.minusSign == dateNumberFormat.minusSign && this.positiveOnly == dateNumberFormat.positiveOnly && Arrays.equals(this.digits, dateNumberFormat.digits);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.digits == null) {
            this.setZeroDigit(this.zeroDigit);
        }
        this.decimalBuf = new char[20];
    }

    @Override
    public Object clone() {
        DateNumberFormat dateNumberFormat = (DateNumberFormat)super.clone();
        dateNumberFormat.digits = (char[])this.digits.clone();
        dateNumberFormat.decimalBuf = new char[20];
        return dateNumberFormat;
    }
}

