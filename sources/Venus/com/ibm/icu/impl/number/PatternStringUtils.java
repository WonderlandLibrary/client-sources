/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.impl.number.PropertiesAffixPatternProvider;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.DecimalFormatSymbols;
import java.math.BigDecimal;

public class PatternStringUtils {
    static final boolean $assertionsDisabled = !PatternStringUtils.class.desiredAssertionStatus();

    public static boolean ignoreRoundingIncrement(BigDecimal bigDecimal, int n) {
        double d = bigDecimal.doubleValue();
        if (d == 0.0) {
            return false;
        }
        if (n < 0) {
            return true;
        }
        int n2 = 0;
        d *= 2.0;
        for (n2 = 0; n2 <= n && d <= 1.0; ++n2, d *= 10.0) {
        }
        return n2 > n;
    }

    public static String propertiesToPatternString(DecimalFormatProperties decimalFormatProperties) {
        int n;
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = 100;
        int n4 = Math.max(0, Math.min(decimalFormatProperties.getGroupingSize(), n3));
        int n5 = Math.max(0, Math.min(decimalFormatProperties.getSecondaryGroupingSize(), n3));
        boolean bl = decimalFormatProperties.getGroupingUsed();
        int n6 = Math.min(decimalFormatProperties.getFormatWidth(), n3);
        Padder.PadPosition padPosition = decimalFormatProperties.getPadPosition();
        String string = decimalFormatProperties.getPadString();
        int n7 = Math.max(0, Math.min(decimalFormatProperties.getMinimumIntegerDigits(), n3));
        int n8 = Math.min(decimalFormatProperties.getMaximumIntegerDigits(), n3);
        int n9 = Math.max(0, Math.min(decimalFormatProperties.getMinimumFractionDigits(), n3));
        int n10 = Math.min(decimalFormatProperties.getMaximumFractionDigits(), n3);
        int n11 = Math.min(decimalFormatProperties.getMinimumSignificantDigits(), n3);
        int n12 = Math.min(decimalFormatProperties.getMaximumSignificantDigits(), n3);
        boolean bl2 = decimalFormatProperties.getDecimalSeparatorAlwaysShown();
        int n13 = Math.min(decimalFormatProperties.getMinimumExponentDigits(), n3);
        boolean bl3 = decimalFormatProperties.getExponentSignAlwaysShown();
        PropertiesAffixPatternProvider propertiesAffixPatternProvider = new PropertiesAffixPatternProvider(decimalFormatProperties);
        stringBuilder.append(propertiesAffixPatternProvider.getString(256));
        int n14 = stringBuilder.length();
        if (!bl) {
            n4 = 0;
            n5 = 0;
        } else if (n4 == n5) {
            n4 = 0;
        }
        int n15 = n4 + n5 + 1;
        BigDecimal bigDecimal = decimalFormatProperties.getRoundingIncrement();
        StringBuilder stringBuilder2 = new StringBuilder();
        int n16 = 0;
        if (n12 != Math.min(n3, -1)) {
            while (stringBuilder2.length() < n11) {
                stringBuilder2.append('@');
            }
            while (stringBuilder2.length() < n12) {
                stringBuilder2.append('#');
            }
        } else if (bigDecimal != null && !PatternStringUtils.ignoreRoundingIncrement(bigDecimal, n10)) {
            n16 = -bigDecimal.scale();
            String string2 = bigDecimal.scaleByPowerOfTen(bigDecimal.scale()).toPlainString();
            if (string2.charAt(0) == '-') {
                stringBuilder2.append(string2, 1, string2.length());
            } else {
                stringBuilder2.append(string2);
            }
        }
        while (stringBuilder2.length() + n16 < n7) {
            stringBuilder2.insert(0, '0');
        }
        while (-n16 < n9) {
            stringBuilder2.append('0');
            --n16;
        }
        int n17 = Math.max(n15, stringBuilder2.length() + n16);
        n17 = n8 != n3 ? Math.max(n8, n17) - 1 : n17 - 1;
        int n18 = n10 != n3 ? Math.min(-n10, n16) : n16;
        for (n2 = n17; n2 >= n18; --n2) {
            n = stringBuilder2.length() + n16 - n2 - 1;
            if (n < 0 || n >= stringBuilder2.length()) {
                stringBuilder.append('#');
            } else {
                stringBuilder.append(stringBuilder2.charAt(n));
            }
            if (n2 == 0 && (bl2 || n18 < 0)) {
                stringBuilder.append('.');
            }
            if (!bl) continue;
            if (n2 > 0 && n2 == n4) {
                stringBuilder.append(',');
            }
            if (n2 <= n4 || n5 <= 0 || (n2 - n4) % n5 != 0) continue;
            stringBuilder.append(',');
        }
        if (n13 != Math.min(n3, -1)) {
            stringBuilder.append('E');
            if (bl3) {
                stringBuilder.append('+');
            }
            for (n2 = 0; n2 < n13; ++n2) {
                stringBuilder.append('0');
            }
        }
        n2 = stringBuilder.length();
        stringBuilder.append(propertiesAffixPatternProvider.getString(0));
        if (n6 > 0) {
            while (n6 - stringBuilder.length() > 0) {
                stringBuilder.insert(n14, '#');
                ++n2;
            }
            switch (1.$SwitchMap$com$ibm$icu$impl$number$Padder$PadPosition[padPosition.ordinal()]) {
                case 1: {
                    n = PatternStringUtils.escapePaddingString(string, stringBuilder, 0);
                    stringBuilder.insert(0, '*');
                    n14 += n + 1;
                    n2 += n + 1;
                    break;
                }
                case 2: {
                    n = PatternStringUtils.escapePaddingString(string, stringBuilder, n14);
                    stringBuilder.insert(n14, '*');
                    n14 += n + 1;
                    n2 += n + 1;
                    break;
                }
                case 3: {
                    PatternStringUtils.escapePaddingString(string, stringBuilder, n2);
                    stringBuilder.insert(n2, '*');
                    break;
                }
                case 4: {
                    stringBuilder.append('*');
                    PatternStringUtils.escapePaddingString(string, stringBuilder, stringBuilder.length());
                }
            }
        }
        if (propertiesAffixPatternProvider.hasNegativeSubpattern()) {
            stringBuilder.append(';');
            stringBuilder.append(propertiesAffixPatternProvider.getString(768));
            stringBuilder.append(stringBuilder, n14, n2);
            stringBuilder.append(propertiesAffixPatternProvider.getString(512));
        }
        return stringBuilder.toString();
    }

    private static int escapePaddingString(CharSequence charSequence, StringBuilder stringBuilder, int n) {
        if (charSequence == null || charSequence.length() == 0) {
            charSequence = " ";
        }
        int n2 = stringBuilder.length();
        if (charSequence.length() == 1) {
            if (charSequence.equals("'")) {
                stringBuilder.insert(n, "''");
            } else {
                stringBuilder.insert(n, charSequence);
            }
        } else {
            stringBuilder.insert(n, '\'');
            int n3 = 1;
            for (int i = 0; i < charSequence.length(); ++i) {
                char c = charSequence.charAt(i);
                if (c == '\'') {
                    stringBuilder.insert(n + n3, "''");
                    n3 += 2;
                    continue;
                }
                stringBuilder.insert(n + n3, c);
                ++n3;
            }
            stringBuilder.insert(n + n3, '\'');
        }
        return stringBuilder.length() - n2;
    }

    public static String convertLocalized(String string, DecimalFormatSymbols decimalFormatSymbols, boolean bl) {
        int n;
        if (string == null) {
            return null;
        }
        String[][] stringArray = new String[21][2];
        int n2 = bl ? 0 : 1;
        int n3 = bl ? 1 : 0;
        stringArray[0][n2] = "%";
        stringArray[0][n3] = decimalFormatSymbols.getPercentString();
        stringArray[5][n2] = "\u2030";
        stringArray[5][n3] = decimalFormatSymbols.getPerMillString();
        stringArray[5][n2] = ".";
        stringArray[5][n3] = decimalFormatSymbols.getDecimalSeparatorString();
        stringArray[5][n2] = ",";
        stringArray[5][n3] = decimalFormatSymbols.getGroupingSeparatorString();
        stringArray[5][n2] = "-";
        stringArray[5][n3] = decimalFormatSymbols.getMinusSignString();
        stringArray[5][n2] = "+";
        stringArray[5][n3] = decimalFormatSymbols.getPlusSignString();
        stringArray[5][n2] = ";";
        stringArray[5][n3] = Character.toString(decimalFormatSymbols.getPatternSeparator());
        stringArray[7][n2] = "@";
        stringArray[7][n3] = Character.toString(decimalFormatSymbols.getSignificantDigit());
        stringArray[8][n2] = "E";
        stringArray[8][n3] = decimalFormatSymbols.getExponentSeparator();
        stringArray[9][n2] = "*";
        stringArray[9][n3] = Character.toString(decimalFormatSymbols.getPadEscape());
        stringArray[10][n2] = "#";
        stringArray[10][n3] = Character.toString(decimalFormatSymbols.getDigit());
        for (n = 0; n < 10; ++n) {
            stringArray[11 + n][n2] = Character.toString((char)(48 + n));
            stringArray[11 + n][n3] = decimalFormatSymbols.getDigitStringsLocal()[n];
        }
        for (n = 0; n < stringArray.length; ++n) {
            stringArray[n][n3] = stringArray[n][n3].replace('\'', '\u2019');
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = 0;
        block2: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\'') {
                if (n4 == 0) {
                    stringBuilder.append('\'');
                    n4 = 1;
                    continue;
                }
                if (n4 == 1) {
                    stringBuilder.append('\'');
                    n4 = 0;
                    continue;
                }
                if (n4 == 2) {
                    n4 = 3;
                    continue;
                }
                if (n4 == 3) {
                    stringBuilder.append('\'');
                    stringBuilder.append('\'');
                    n4 = 1;
                    continue;
                }
                if (n4 == 4) {
                    n4 = 5;
                    continue;
                }
                if (!$assertionsDisabled && n4 != 5) {
                    throw new AssertionError();
                }
                stringBuilder.append('\'');
                stringBuilder.append('\'');
                n4 = 4;
                continue;
            }
            if (n4 == 0 || n4 == 3 || n4 == 4) {
                for (String[] stringArray2 : stringArray) {
                    if (!string.regionMatches(i, stringArray2[0], 0, stringArray2[0].length())) continue;
                    i += stringArray2[0].length() - 1;
                    if (n4 == 3 || n4 == 4) {
                        stringBuilder.append('\'');
                        n4 = 0;
                    }
                    stringBuilder.append(stringArray2[5]);
                    continue block2;
                }
                for (String[] stringArray2 : stringArray) {
                    if (!string.regionMatches(i, stringArray2[5], 0, stringArray2[5].length())) continue;
                    if (n4 == 0) {
                        stringBuilder.append('\'');
                        n4 = 4;
                    }
                    stringBuilder.append(c);
                    continue block2;
                }
                if (n4 == 3 || n4 == 4) {
                    stringBuilder.append('\'');
                    n4 = 0;
                }
                stringBuilder.append(c);
                continue;
            }
            if (!$assertionsDisabled && n4 != 1 && n4 != 2 && n4 != 5) {
                throw new AssertionError();
            }
            stringBuilder.append(c);
            n4 = 2;
        }
        if (n4 == 3 || n4 == 4) {
            stringBuilder.append('\'');
            n4 = 0;
        }
        if (n4 != 0) {
            throw new IllegalArgumentException("Malformed localized pattern: unterminated quote");
        }
        return stringBuilder.toString();
    }

    public static void patternInfoToStringBuilder(AffixPatternProvider affixPatternProvider, boolean bl, int n, NumberFormatter.SignDisplay signDisplay, StandardPlural standardPlural, boolean bl2, StringBuilder stringBuilder) {
        boolean bl3 = n != -1 && (signDisplay == NumberFormatter.SignDisplay.ALWAYS || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || n == 1 && (signDisplay == NumberFormatter.SignDisplay.EXCEPT_ZERO || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO)) && !affixPatternProvider.positiveHasPlusSign();
        boolean bl4 = affixPatternProvider.hasNegativeSubpattern() && (n == -1 || affixPatternProvider.negativeHasMinusSign() && bl3);
        int n2 = 0;
        if (bl4) {
            n2 |= 0x200;
        }
        if (bl) {
            n2 |= 0x100;
        }
        if (standardPlural != null) {
            if (!$assertionsDisabled && standardPlural.ordinal() != (0xFF & standardPlural.ordinal())) {
                throw new AssertionError();
            }
            n2 |= standardPlural.ordinal();
        }
        boolean bl5 = !bl || bl4 ? false : (n == -1 ? signDisplay != NumberFormatter.SignDisplay.NEVER : bl3);
        int n3 = affixPatternProvider.length(n2) + (bl5 ? 1 : 0);
        stringBuilder.setLength(0);
        for (int i = 0; i < n3; ++i) {
            int n4 = bl5 && i == 0 ? 45 : (bl5 ? (int)affixPatternProvider.charAt(n2, i - 1) : (int)affixPatternProvider.charAt(n2, i));
            if (bl3 && n4 == 45) {
                n4 = 43;
            }
            if (bl2 && n4 == 37) {
                n4 = 8240;
            }
            stringBuilder.append((char)n4);
        }
    }
}

