/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CalType;
import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Arrays;

public class EraRules {
    private static final int MAX_ENCODED_START_YEAR = Short.MAX_VALUE;
    private static final int MIN_ENCODED_START_YEAR = Short.MIN_VALUE;
    public static final int MIN_ENCODED_START = EraRules.encodeDate(Short.MIN_VALUE, 1, 1);
    private static final int YEAR_MASK = -65536;
    private static final int MONTH_MASK = 65280;
    private static final int DAY_MASK = 255;
    private int[] startDates;
    private int numEras;
    private int currentEra;

    private EraRules(int[] nArray, int n) {
        this.startDates = nArray;
        this.numEras = n;
        this.initCurrentEra();
    }

    public static EraRules getInstance(CalType calType, boolean bl) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("calendarData");
        UResourceBundle uResourceBundle3 = uResourceBundle2.get(calType.getId());
        UResourceBundle uResourceBundle4 = uResourceBundle3.get("eras");
        int n = uResourceBundle4.getSize();
        int n2 = Integer.MAX_VALUE;
        int[] nArray = new int[n];
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle4.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            UResourceBundle uResourceBundle5 = uResourceBundleIterator.next();
            String string = uResourceBundle5.getKey();
            int n3 = -1;
            try {
                n3 = Integer.parseInt(string);
            } catch (NumberFormatException numberFormatException) {
                throw new ICUException("Invald era rule key:" + string + " in era rule data for " + calType.getId());
            }
            if (n3 < 0 || n3 >= n) {
                throw new ICUException("Era rule key:" + string + " in era rule data for " + calType.getId() + " must be in range [0, " + (n - 1) + "]");
            }
            if (EraRules.isSet(nArray[n3])) {
                throw new ICUException("Dupulicated era rule for rule key:" + string + " in era rule data for " + calType.getId());
            }
            boolean bl2 = true;
            boolean bl3 = false;
            UResourceBundleIterator uResourceBundleIterator2 = uResourceBundle5.getIterator();
            while (uResourceBundleIterator2.hasNext()) {
                Object object;
                UResourceBundle uResourceBundle6 = uResourceBundleIterator2.next();
                String string2 = uResourceBundle6.getKey();
                if (string2.equals("start")) {
                    object = uResourceBundle6.getIntVector();
                    if (((int[])object).length != 3 || !EraRules.isValidRuleStartDate(object[0], (int)object[1], (int)object[2])) {
                        throw new ICUException("Invalid era rule date data:" + Arrays.toString(object) + " in era rule data for " + calType.getId());
                    }
                    nArray[n3] = EraRules.encodeDate((int)object[0], (int)object[1], (int)object[2]);
                    continue;
                }
                if (string2.equals("named")) {
                    object = uResourceBundle6.getString();
                    if (!object.equals("false")) continue;
                    bl2 = false;
                    continue;
                }
                if (!string2.equals("end")) continue;
                bl3 = true;
            }
            if (EraRules.isSet(nArray[n3])) {
                if (bl3) {
                    // empty if block
                }
            } else if (bl3) {
                if (n3 != 0) {
                    throw new ICUException("Era data for " + string + " in era rule data for " + calType.getId() + " has only end rule.");
                }
                nArray[n3] = MIN_ENCODED_START;
            } else {
                throw new ICUException("Missing era start/end rule date for key:" + string + " in era rule data for " + calType.getId());
            }
            if (bl2) {
                if (n3 < n2) continue;
                throw new ICUException("Non-tentative era(" + n3 + ") must be placed before the first tentative era");
            }
            if (n3 >= n2) continue;
            n2 = n3;
        }
        if (n2 < Integer.MAX_VALUE && !bl) {
            return new EraRules(nArray, n2);
        }
        return new EraRules(nArray, n);
    }

    public int getNumberOfEras() {
        return this.numEras;
    }

    public int[] getStartDate(int n, int[] nArray) {
        if (n < 0 || n >= this.numEras) {
            throw new IllegalArgumentException("eraIdx is out of range");
        }
        return EraRules.decodeDate(this.startDates[n], nArray);
    }

    public int getStartYear(int n) {
        if (n < 0 || n >= this.numEras) {
            throw new IllegalArgumentException("eraIdx is out of range");
        }
        int[] nArray = EraRules.decodeDate(this.startDates[n], null);
        return nArray[0];
    }

    public int getEraIndex(int n, int n2, int n3) {
        if (n2 < 1 || n2 > 12 || n3 < 1 || n3 > 31) {
            throw new IllegalArgumentException("Illegal date - year:" + n + "month:" + n2 + "day:" + n3);
        }
        int n4 = this.numEras;
        int n5 = EraRules.compareEncodedDateWithYMD(this.startDates[this.getCurrentEraIndex()], n, n2, n3) <= 0 ? this.getCurrentEraIndex() : 0;
        while (n5 < n4 - 1) {
            int n6 = (n5 + n4) / 2;
            if (EraRules.compareEncodedDateWithYMD(this.startDates[n6], n, n2, n3) <= 0) {
                n5 = n6;
                continue;
            }
            n4 = n6;
        }
        return n5;
    }

    public int getCurrentEraIndex() {
        return this.currentEra;
    }

    private void initCurrentEra() {
        int n;
        long l = System.currentTimeMillis();
        TimeZone timeZone = TimeZone.getDefault();
        l += (long)timeZone.getOffset(l);
        int[] nArray = Grego.timeToFields(l, null);
        int n2 = EraRules.encodeDate(nArray[0], nArray[1] + 1, nArray[2]);
        for (n = this.numEras - 1; n > 0 && n2 < this.startDates[n]; --n) {
        }
        this.currentEra = n;
    }

    private static boolean isSet(int n) {
        return n != 0;
    }

    private static boolean isValidRuleStartDate(int n, int n2, int n3) {
        return n >= Short.MIN_VALUE && n <= Short.MAX_VALUE && n2 >= 1 && n2 <= 12 && n3 >= 1 && n3 <= 31;
    }

    private static int encodeDate(int n, int n2, int n3) {
        return n << 16 | n2 << 8 | n3;
    }

    private static int[] decodeDate(int n, int[] nArray) {
        int n2;
        int n3;
        int n4;
        if (n == MIN_ENCODED_START) {
            n4 = Integer.MIN_VALUE;
            n3 = 1;
            n2 = 1;
        } else {
            n4 = (n & 0xFFFF0000) >> 16;
            n3 = (n & 0xFF00) >> 8;
            n2 = n & 0xFF;
        }
        if (nArray != null && nArray.length >= 3) {
            nArray[0] = n4;
            nArray[1] = n3;
            nArray[2] = n2;
            return nArray;
        }
        int[] nArray2 = new int[]{n4, n3, n2};
        return nArray2;
    }

    private static int compareEncodedDateWithYMD(int n, int n2, int n3, int n4) {
        if (n2 < Short.MIN_VALUE) {
            if (n == MIN_ENCODED_START) {
                if (n2 > Integer.MIN_VALUE || n3 > 1 || n4 > 1) {
                    return 1;
                }
                return 1;
            }
            return 0;
        }
        if (n2 > Short.MAX_VALUE) {
            return 1;
        }
        int n5 = EraRules.encodeDate(n2, n3, n4);
        if (n < n5) {
            return 1;
        }
        if (n == n5) {
            return 1;
        }
        return 0;
    }
}

