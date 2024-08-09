/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.impl.duration.impl.DataRecord;
import com.ibm.icu.impl.duration.impl.Utils;
import java.util.Arrays;

public class PeriodFormatterData {
    final DataRecord dr;
    String localeName;
    public static boolean trace = false;
    private static final int FORM_PLURAL = 0;
    private static final int FORM_SINGULAR = 1;
    private static final int FORM_DUAL = 2;
    private static final int FORM_PAUCAL = 3;
    private static final int FORM_SINGULAR_SPELLED = 4;
    private static final int FORM_SINGULAR_NO_OMIT = 5;
    private static final int FORM_HALF_SPELLED = 6;

    public PeriodFormatterData(String string, DataRecord dataRecord) {
        this.dr = dataRecord;
        this.localeName = string;
        if (string == null) {
            throw new NullPointerException("localename is null");
        }
        if (dataRecord == null) {
            throw new NullPointerException("data record is null");
        }
    }

    public int pluralization() {
        return this.dr.pl;
    }

    public boolean allowZero() {
        return this.dr.allowZero;
    }

    public boolean weeksAloneOnly() {
        return this.dr.weeksAloneOnly;
    }

    public int useMilliseconds() {
        return this.dr.useMilliseconds;
    }

    public boolean appendPrefix(int n, int n2, StringBuffer stringBuffer) {
        String string;
        int n3;
        DataRecord.ScopeData scopeData;
        if (this.dr.scopeData != null && (scopeData = this.dr.scopeData[n3 = n * 3 + n2]) != null && (string = scopeData.prefix) != null) {
            stringBuffer.append(string);
            return scopeData.requiresDigitPrefix;
        }
        return true;
    }

    public void appendSuffix(int n, int n2, StringBuffer stringBuffer) {
        String string;
        int n3;
        DataRecord.ScopeData scopeData;
        if (this.dr.scopeData != null && (scopeData = this.dr.scopeData[n3 = n * 3 + n2]) != null && (string = scopeData.suffix) != null) {
            if (trace) {
                System.out.println("appendSuffix '" + string + "'");
            }
            stringBuffer.append(string);
        }
    }

    public boolean appendUnit(TimeUnit timeUnit, int n, int n2, int n3, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, StringBuffer stringBuffer) {
        int n4 = timeUnit.ordinal();
        boolean bl6 = false;
        if (this.dr.requiresSkipMarker != null && this.dr.requiresSkipMarker[n4] && this.dr.skippedUnitMarker != null) {
            if (!bl5 && bl4) {
                stringBuffer.append(this.dr.skippedUnitMarker);
            }
            bl6 = true;
        }
        if (n3 != 0) {
            String[] stringArray;
            boolean bl7 = n3 == 1;
            String[] stringArray2 = stringArray = bl7 ? this.dr.mediumNames : this.dr.shortNames;
            if (stringArray == null || stringArray[n4] == null) {
                String[] stringArray3 = stringArray = bl7 ? this.dr.shortNames : this.dr.mediumNames;
            }
            if (stringArray != null && stringArray[n4] != null) {
                this.appendCount(timeUnit, false, false, n, n2, bl, stringArray[n4], bl4, stringBuffer);
                return true;
            }
        }
        if (n2 == 2 && this.dr.halfSupport != null) {
            switch (this.dr.halfSupport[n4]) {
                case 0: {
                    break;
                }
                case 2: {
                    if (n > 1000) break;
                }
                case 1: {
                    n = n / 500 * 500;
                    n2 = 3;
                }
            }
        }
        String string = null;
        int n5 = this.computeForm(timeUnit, n, n2, bl3 && bl4);
        if (n5 == 4) {
            if (this.dr.singularNames == null) {
                n5 = 1;
                string = this.dr.pluralNames[n4][n5];
            } else {
                string = this.dr.singularNames[n4];
            }
        } else if (n5 == 5) {
            string = this.dr.pluralNames[n4][1];
        } else if (n5 == 6) {
            string = this.dr.halfNames[n4];
        } else {
            try {
                string = this.dr.pluralNames[n4][n5];
            } catch (NullPointerException nullPointerException) {
                System.out.println("Null Pointer in PeriodFormatterData[" + this.localeName + "].au px: " + n4 + " form: " + n5 + " pn: " + Arrays.toString((Object[])this.dr.pluralNames));
                throw nullPointerException;
            }
        }
        if (string == null) {
            n5 = 0;
            string = this.dr.pluralNames[n4][n5];
        }
        boolean bl8 = n5 == 4 || n5 == 6 || this.dr.omitSingularCount && n5 == 1 || this.dr.omitDualCount && n5 == 2;
        int n6 = this.appendCount(timeUnit, bl8, bl2, n, n2, bl, string, bl4, stringBuffer);
        if (bl4 && n6 >= 0) {
            String string2 = null;
            if (this.dr.rqdSuffixes != null && n6 < this.dr.rqdSuffixes.length) {
                string2 = this.dr.rqdSuffixes[n6];
            }
            if (string2 == null && this.dr.optSuffixes != null && n6 < this.dr.optSuffixes.length) {
                string2 = this.dr.optSuffixes[n6];
            }
            if (string2 != null) {
                stringBuffer.append(string2);
            }
        }
        return bl6;
    }

    public int appendCount(TimeUnit timeUnit, boolean bl, boolean bl2, int n, int n2, boolean bl3, String string, boolean bl4, StringBuffer stringBuffer) {
        String string2;
        if (n2 == 2 && this.dr.halves == null) {
            n2 = 0;
        }
        if (!bl && bl2 && this.dr.digitPrefix != null) {
            stringBuffer.append(this.dr.digitPrefix);
        }
        int n3 = timeUnit.ordinal();
        block0 : switch (n2) {
            case 0: {
                if (bl) break;
                this.appendInteger(n / 1000, 1, 10, stringBuffer);
                break;
            }
            case 1: {
                int n4 = n / 1000;
                if (timeUnit == TimeUnit.MINUTE && (this.dr.fiveMinutes != null || this.dr.fifteenMinutes != null) && n4 != 0 && n4 % 5 == 0) {
                    if (this.dr.fifteenMinutes != null && (n4 == 15 || n4 == 45)) {
                        int n5 = n4 = n4 == 15 ? 1 : 3;
                        if (!bl) {
                            this.appendInteger(n4, 1, 10, stringBuffer);
                        }
                        string = this.dr.fifteenMinutes;
                        n3 = 8;
                        break;
                    }
                    if (this.dr.fiveMinutes != null) {
                        n4 /= 5;
                        if (!bl) {
                            this.appendInteger(n4, 1, 10, stringBuffer);
                        }
                        string = this.dr.fiveMinutes;
                        n3 = 9;
                        break;
                    }
                }
                if (bl) break;
                this.appendInteger(n4, 1, 10, stringBuffer);
                break;
            }
            case 2: {
                int n6;
                int n4 = n / 500;
                if (n4 != 1 && !bl) {
                    this.appendCountValue(n, 1, 0, stringBuffer);
                }
                if ((n4 & 1) != 1) break;
                if (n4 == 1 && this.dr.halfNames != null && this.dr.halfNames[n3] != null) {
                    stringBuffer.append(string);
                    return bl4 ? n3 : -1;
                }
                int n7 = n6 = n4 == 1 ? 0 : 1;
                if (this.dr.genders != null && this.dr.halves.length > 2 && this.dr.genders[n3] == 1) {
                    n6 += 2;
                }
                byte by = this.dr.halfPlacements == null ? (byte)0 : this.dr.halfPlacements[n6 & 1];
                String string3 = this.dr.halves[n6];
                String string4 = this.dr.measures == null ? null : this.dr.measures[n3];
                switch (by) {
                    case 0: {
                        stringBuffer.append(string3);
                        break block0;
                    }
                    case 1: {
                        if (string4 != null) {
                            stringBuffer.append(string4);
                            stringBuffer.append(string3);
                            if (bl3 && !bl) {
                                stringBuffer.append(this.dr.countSep);
                            }
                        } else {
                            stringBuffer.append(string);
                            stringBuffer.append(string3);
                            return bl4 ? n3 : -1;
                        }
                        stringBuffer.append(string);
                        return 1;
                    }
                    case 2: {
                        if (string4 != null) {
                            stringBuffer.append(string4);
                        }
                        if (bl3 && !bl) {
                            stringBuffer.append(this.dr.countSep);
                        }
                        stringBuffer.append(string);
                        stringBuffer.append(string3);
                        return bl4 ? n3 : -1;
                    }
                }
                break;
            }
            default: {
                int n4 = 1;
                switch (n2) {
                    case 4: {
                        n4 = 2;
                        break;
                    }
                    case 5: {
                        n4 = 3;
                        break;
                    }
                }
                if (bl) break;
                this.appendCountValue(n, 1, n4, stringBuffer);
            }
        }
        if (!bl && bl3) {
            stringBuffer.append(this.dr.countSep);
        }
        if (!bl && this.dr.measures != null && n3 < this.dr.measures.length && (string2 = this.dr.measures[n3]) != null) {
            stringBuffer.append(string2);
        }
        stringBuffer.append(string);
        return bl4 ? n3 : -1;
    }

    public void appendCountValue(int n, int n2, int n3, StringBuffer stringBuffer) {
        int n4 = n / 1000;
        if (n3 == 0) {
            this.appendInteger(n4, n2, 10, stringBuffer);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(' ');
        }
        this.appendDigits(n4, n2, 10, stringBuffer);
        int n5 = n % 1000;
        if (n3 == 1) {
            n5 /= 100;
        } else if (n3 == 2) {
            n5 /= 10;
        }
        stringBuffer.append(this.dr.decimalSep);
        this.appendDigits(n5, n3, n3, stringBuffer);
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(' ');
        }
    }

    public void appendInteger(int n, int n2, int n3, StringBuffer stringBuffer) {
        String string;
        if (this.dr.numberNames != null && n < this.dr.numberNames.length && (string = this.dr.numberNames[n]) != null) {
            stringBuffer.append(string);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(' ');
        }
        switch (this.dr.numberSystem) {
            case 0: {
                this.appendDigits(n, n2, n3, stringBuffer);
                break;
            }
            case 1: {
                stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.TRADITIONAL));
                break;
            }
            case 2: {
                stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.SIMPLIFIED));
                break;
            }
            case 3: {
                stringBuffer.append(Utils.chineseNumber(n, Utils.ChineseDigits.KOREAN));
            }
        }
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(' ');
        }
    }

    public void appendDigits(long l, int n, int n2, StringBuffer stringBuffer) {
        char[] cArray = new char[n2];
        int n3 = n2;
        while (n3 > 0 && l > 0L) {
            cArray[--n3] = (char)((long)this.dr.zero + l % 10L);
            l /= 10L;
        }
        int n4 = n2 - n;
        while (n3 > n4) {
            cArray[--n3] = this.dr.zero;
        }
        stringBuffer.append(cArray, n3, n2 - n3);
    }

    public void appendSkippedUnit(StringBuffer stringBuffer) {
        if (this.dr.skippedUnitMarker != null) {
            stringBuffer.append(this.dr.skippedUnitMarker);
        }
    }

    public boolean appendUnitSeparator(TimeUnit timeUnit, boolean bl, boolean bl2, boolean bl3, StringBuffer stringBuffer) {
        if (bl && this.dr.unitSep != null || this.dr.shortUnitSep != null) {
            if (bl && this.dr.unitSep != null) {
                int n = (bl2 ? 2 : 0) + (bl3 ? 1 : 0);
                stringBuffer.append(this.dr.unitSep[n]);
                return this.dr.unitSepRequiresDP != null && this.dr.unitSepRequiresDP[n];
            }
            stringBuffer.append(this.dr.shortUnitSep);
        }
        return true;
    }

    private int computeForm(TimeUnit timeUnit, int n, int n2, boolean bl) {
        int n3;
        if (trace) {
            System.err.println("pfd.cf unit: " + timeUnit + " count: " + n + " cv: " + n2 + " dr.pl: " + this.dr.pl);
            Thread.dumpStack();
        }
        if (this.dr.pl == 0) {
            return 1;
        }
        int n4 = n / 1000;
        block0 : switch (n2) {
            case 0: 
            case 1: {
                break;
            }
            case 2: {
                switch (this.dr.fractionHandling) {
                    case 0: {
                        return 1;
                    }
                    case 1: 
                    case 2: {
                        n3 = n / 500;
                        if (n3 == 1) {
                            if (this.dr.halfNames != null && this.dr.halfNames[timeUnit.ordinal()] != null) {
                                return 1;
                            }
                            return 0;
                        }
                        if ((n3 & 1) != 1) break block0;
                        if (this.dr.pl == 5 && n3 > 21) {
                            return 0;
                        }
                        if (n3 != 3 || this.dr.pl != 1 || this.dr.fractionHandling == 2) break block0;
                        return 1;
                    }
                    case 3: {
                        n3 = n / 500;
                        if (n3 != 1 && n3 != 3) break block0;
                        return 0;
                    }
                    default: {
                        throw new IllegalStateException();
                    }
                }
            }
            default: {
                switch (this.dr.decimalHandling) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        return 0;
                    }
                    case 2: {
                        if (n >= 1000) break;
                        return 0;
                    }
                    case 3: {
                        if (this.dr.pl != 3) break;
                        return 0;
                    }
                }
                return 1;
            }
        }
        if (trace && n == 0) {
            System.err.println("EZeroHandling = " + this.dr.zeroHandling);
        }
        if (n == 0 && this.dr.zeroHandling == 1) {
            return 1;
        }
        n3 = 0;
        switch (this.dr.pl) {
            case 0: {
                break;
            }
            case 1: {
                if (n4 != 1) break;
                n3 = 4;
                break;
            }
            case 2: {
                if (n4 == 2) {
                    n3 = 2;
                    break;
                }
                if (n4 != 1) break;
                n3 = 1;
                break;
            }
            case 3: {
                int n5 = n4;
                if ((n5 %= 100) > 20) {
                    n5 %= 10;
                }
                if (n5 == 1) {
                    n3 = 1;
                    break;
                }
                if (n5 <= 1 || n5 >= 5) break;
                n3 = 3;
                break;
            }
            case 4: {
                if (n4 == 2) {
                    n3 = 2;
                    break;
                }
                if (n4 == 1) {
                    if (bl) {
                        n3 = 4;
                        break;
                    }
                    n3 = 1;
                    break;
                }
                if (timeUnit != TimeUnit.YEAR || n4 <= 11) break;
                n3 = 5;
                break;
            }
            case 5: {
                if (n4 == 2) {
                    n3 = 2;
                    break;
                }
                if (n4 == 1) {
                    n3 = 1;
                    break;
                }
                if (n4 <= 10) break;
                n3 = 5;
                break;
            }
            default: {
                System.err.println("dr.pl is " + this.dr.pl);
                throw new IllegalStateException();
            }
        }
        return n3;
    }
}

