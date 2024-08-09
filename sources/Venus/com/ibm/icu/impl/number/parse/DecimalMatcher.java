/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

public class DecimalMatcher
implements NumberParseMatcher {
    private final boolean requireGroupingMatch;
    private final boolean groupingDisabled;
    private final boolean integerOnly;
    private final int grouping1;
    private final int grouping2;
    private final String groupingSeparator;
    private final String decimalSeparator;
    private final UnicodeSet groupingUniSet;
    private final UnicodeSet decimalUniSet;
    private final UnicodeSet separatorSet;
    private final UnicodeSet leadSet;
    private final String[] digitStrings;
    static final boolean $assertionsDisabled = !DecimalMatcher.class.desiredAssertionStatus();

    public static DecimalMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int n) {
        return new DecimalMatcher(decimalFormatSymbols, grouper, n);
    }

    private DecimalMatcher(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int n) {
        if (0 != (n & 2)) {
            this.groupingSeparator = decimalFormatSymbols.getMonetaryGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getMonetaryDecimalSeparatorString();
        } else {
            this.groupingSeparator = decimalFormatSymbols.getGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getDecimalSeparatorString();
        }
        boolean bl = 0 != (n & 4);
        StaticUnicodeSets.Key key = bl ? StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS : StaticUnicodeSets.Key.ALL_SEPARATORS;
        this.groupingUniSet = StaticUnicodeSets.get(key);
        StaticUnicodeSets.Key key2 = StaticUnicodeSets.chooseFrom(this.decimalSeparator, bl ? StaticUnicodeSets.Key.STRICT_COMMA : StaticUnicodeSets.Key.COMMA, bl ? StaticUnicodeSets.Key.STRICT_PERIOD : StaticUnicodeSets.Key.PERIOD);
        this.decimalUniSet = key2 != null ? StaticUnicodeSets.get(key2) : (!this.decimalSeparator.isEmpty() ? new UnicodeSet().add(this.decimalSeparator.codePointAt(0)).freeze() : UnicodeSet.EMPTY);
        if (key != null && key2 != null) {
            this.separatorSet = this.groupingUniSet;
            this.leadSet = StaticUnicodeSets.get(bl ? StaticUnicodeSets.Key.DIGITS_OR_ALL_SEPARATORS : StaticUnicodeSets.Key.DIGITS_OR_STRICT_ALL_SEPARATORS);
        } else {
            this.separatorSet = new UnicodeSet().addAll(this.groupingUniSet).addAll(this.decimalUniSet).freeze();
            this.leadSet = null;
        }
        int n2 = decimalFormatSymbols.getCodePointZero();
        this.digitStrings = n2 == -1 || !UCharacter.isDigit(n2) || UCharacter.digit(n2) != 0 ? decimalFormatSymbols.getDigitStringsLocal() : null;
        this.requireGroupingMatch = 0 != (n & 8);
        this.groupingDisabled = 0 != (n & 0x20);
        this.integerOnly = 0 != (n & 0x10);
        this.grouping1 = grouper.getPrimary();
        this.grouping2 = grouper.getSecondary();
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return this.match(stringSegment, parsedNumber, 1);
    }

    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber, int n) {
        int n2;
        int n3;
        int n4;
        byte by;
        if (parsedNumber.seenNumber() && n == 0) {
            return true;
        }
        if (n != 0 && !$assertionsDisabled && parsedNumber.quantity == null) {
            throw new AssertionError();
        }
        int n5 = stringSegment.getOffset();
        boolean bl = false;
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = null;
        int n6 = 0;
        String string = null;
        String string2 = null;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = -1;
        int n11 = -1;
        int n12 = -1;
        while (stringSegment.length() > 0) {
            int n13;
            bl = false;
            by = -1;
            n4 = stringSegment.getCodePoint();
            if (UCharacter.isDigit(n4)) {
                stringSegment.adjustOffset(Character.charCount(n4));
                by = (byte)UCharacter.digit(n4);
            }
            if (by == -1 && this.digitStrings != null) {
                for (n3 = 0; n3 < this.digitStrings.length; ++n3) {
                    String string3 = this.digitStrings[n3];
                    if (string3.isEmpty()) continue;
                    n13 = stringSegment.getCommonPrefixLength(string3);
                    if (n13 == string3.length()) {
                        stringSegment.adjustOffset(n13);
                        by = (byte)n3;
                        break;
                    }
                    bl = bl || n13 == stringSegment.length();
                }
            }
            if (by >= 0) {
                if (decimalQuantity_DualStorageBCD == null) {
                    decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD();
                }
                decimalQuantity_DualStorageBCD.appendDigit(by, 0, false);
                ++n9;
                if (string2 == null) continue;
                ++n6;
                continue;
            }
            n3 = 0;
            boolean bl2 = false;
            if (string2 == null && !this.decimalSeparator.isEmpty()) {
                n13 = stringSegment.getCommonPrefixLength(this.decimalSeparator);
                boolean bl3 = bl = bl || n13 == stringSegment.length();
                if (n13 == this.decimalSeparator.length()) {
                    n3 = 1;
                    string2 = this.decimalSeparator;
                }
            }
            if (string != null) {
                n13 = stringSegment.getCommonPrefixLength(string);
                boolean bl4 = bl = bl || n13 == stringSegment.length();
                if (n13 == string.length()) {
                    bl2 = true;
                }
            }
            if (!this.groupingDisabled && string == null && string2 == null && !this.groupingSeparator.isEmpty()) {
                n13 = stringSegment.getCommonPrefixLength(this.groupingSeparator);
                boolean bl5 = bl = bl || n13 == stringSegment.length();
                if (n13 == this.groupingSeparator.length()) {
                    bl2 = true;
                    string = this.groupingSeparator;
                }
            }
            if (!bl2 && string2 == null && this.decimalUniSet.contains(n4)) {
                n3 = 1;
                string2 = UCharacter.toString(n4);
            }
            if (!this.groupingDisabled && string == null && string2 == null && this.groupingUniSet.contains(n4)) {
                bl2 = true;
                string = UCharacter.toString(n4);
            }
            if (n3 == 0 && !bl2 || n3 != 0 && this.integerOnly || n8 == 2 && bl2) break;
            n13 = this.validateGroup(n11, n12, false) ? 1 : 0;
            n2 = this.validateGroup(n8, n9, true);
            if (n13 == 0 || n3 != 0 && n2 == 0) {
                if (bl2 && n9 == 0) {
                    if (!$assertionsDisabled && n8 != 1) {
                        throw new AssertionError();
                    }
                    break;
                }
                if (!this.requireGroupingMatch) break;
                decimalQuantity_DualStorageBCD = null;
                break;
            }
            if (this.requireGroupingMatch && n9 == 0 && n8 == 1) break;
            n10 = n7;
            n12 = n9;
            n11 = n3 != 0 ? -1 : n8;
            if (n9 != 0) {
                n7 = stringSegment.getOffset();
            }
            n8 = bl2 ? 1 : 2;
            n9 = 0;
            if (bl2) {
                stringSegment.adjustOffset(string.length());
                continue;
            }
            stringSegment.adjustOffset(string2.length());
        }
        if (n8 != 2 && n9 == 0) {
            bl = true;
            stringSegment.setOffset(n7);
            n7 = n10;
            n8 = n11;
            n9 = n12;
            n10 = -1;
            n11 = 0;
            n12 = 1;
        }
        by = (byte)(this.validateGroup(n11, n12, false) ? 1 : 0);
        n4 = this.validateGroup(n8, n9, true) ? 1 : 0;
        if (!this.requireGroupingMatch) {
            n3 = 0;
            if (by == 0) {
                stringSegment.setOffset(n10);
                n3 += n12;
                n3 += n9;
            } else if (n4 == 0 && (n11 != 0 || n12 != 0)) {
                bl = true;
                stringSegment.setOffset(n7);
                n3 += n9;
            }
            if (n3 != 0) {
                decimalQuantity_DualStorageBCD.adjustMagnitude(-n3);
                decimalQuantity_DualStorageBCD.truncate();
            }
            by = 1;
            n4 = 1;
        }
        if (n8 != 2 && (by == 0 || n4 == 0)) {
            decimalQuantity_DualStorageBCD = null;
        }
        if (decimalQuantity_DualStorageBCD == null) {
            bl = bl || stringSegment.length() == 0;
            stringSegment.setOffset(n5);
            return bl;
        }
        decimalQuantity_DualStorageBCD.adjustMagnitude(-n6);
        if (n != 0 && stringSegment.getOffset() != n5) {
            n3 = 0;
            if (decimalQuantity_DualStorageBCD.fitsInLong()) {
                long l = decimalQuantity_DualStorageBCD.toLong(true);
                if (!$assertionsDisabled && l < 0L) {
                    throw new AssertionError();
                }
                if (l <= Integer.MAX_VALUE) {
                    n2 = (int)l;
                    try {
                        parsedNumber.quantity.adjustMagnitude(n * n2);
                    } catch (ArithmeticException arithmeticException) {
                        n3 = 1;
                    }
                } else {
                    n3 = 1;
                }
            } else {
                n3 = 1;
            }
            if (n3 != 0) {
                if (n == -1) {
                    parsedNumber.quantity.clear();
                } else {
                    parsedNumber.quantity = null;
                    parsedNumber.flags |= 0x80;
                }
            }
        } else {
            parsedNumber.quantity = decimalQuantity_DualStorageBCD;
        }
        if (string2 != null) {
            parsedNumber.flags |= 0x20;
        }
        parsedNumber.setCharsConsumed(stringSegment);
        return stringSegment.length() == 0 || bl;
    }

    private boolean validateGroup(int n, int n2, boolean bl) {
        if (this.requireGroupingMatch) {
            if (n == -1) {
                return false;
            }
            if (n == 0) {
                if (bl) {
                    return false;
                }
                return n2 != 0 && n2 <= this.grouping2;
            }
            if (n == 1) {
                if (bl) {
                    return n2 == this.grouping1;
                }
                return n2 == this.grouping2;
            }
            if (!$assertionsDisabled && n != 2) {
                throw new AssertionError();
            }
            return false;
        }
        if (n == 1) {
            return n2 != 1;
        }
        return false;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        if (this.digitStrings == null && this.leadSet != null) {
            return stringSegment.startsWith(this.leadSet);
        }
        if (stringSegment.startsWith(this.separatorSet) || UCharacter.isDigit(stringSegment.getCodePoint())) {
            return false;
        }
        if (this.digitStrings == null) {
            return true;
        }
        for (int i = 0; i < this.digitStrings.length; ++i) {
            if (!stringSegment.startsWith(this.digitStrings[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<DecimalMatcher>";
    }
}

