/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.SimpleTimeZone;
import com.ibm.icu.util.TimeArrayTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.MissingResourceException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OlsonTimeZone
extends BasicTimeZone {
    static final long serialVersionUID = -6281977362477515376L;
    private static final int MAX_OFFSET_SECONDS = 86400;
    private int transitionCount;
    private int typeCount;
    private long[] transitionTimes64;
    private int[] typeOffsets;
    private byte[] typeMapData;
    private int finalStartYear = Integer.MAX_VALUE;
    private double finalStartMillis = Double.MAX_VALUE;
    private SimpleTimeZone finalZone = null;
    private volatile String canonicalID = null;
    private static final String ZONEINFORES = "zoneinfo64";
    private static final boolean DEBUG;
    private static final int SECONDS_PER_DAY = 86400;
    private transient InitialTimeZoneRule initialRule;
    private transient TimeZoneTransition firstTZTransition;
    private transient int firstTZTransitionIdx;
    private transient TimeZoneTransition firstFinalTZTransition;
    private transient TimeArrayTimeZoneRule[] historicRules;
    private transient SimpleTimeZone finalZoneWithStartYear;
    private transient boolean transitionRulesInitialized;
    private static final int currentSerialVersion = 1;
    private int serialVersionOnStream = 1;
    private volatile transient boolean isFrozen = false;
    static final boolean $assertionsDisabled;

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException("Month is not in the legal range: " + n3);
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
    }

    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n != 1 && n != 0 || n3 < 0 || n3 > 11 || n4 < 1 || n4 > n7 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000 || n7 < 28 || n7 > 31) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            n2 = -n2;
        }
        if (this.finalZone != null && n2 >= this.finalStartYear) {
            return this.finalZone.getOffset(n, n2, n3, n4, n5, n6);
        }
        long l = Grego.fieldsToDay(n2, n3, n4) * 86400000L + (long)n6;
        int[] nArray = new int[2];
        this.getHistoricalOffset(l, true, 3, 1, nArray);
        return nArray[0] + nArray[1];
    }

    @Override
    public void setRawOffset(int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (this.getRawOffset() == n) {
            return;
        }
        long l = System.currentTimeMillis();
        if ((double)l < this.finalStartMillis) {
            Object[] objectArray;
            SimpleTimeZone simpleTimeZone = new SimpleTimeZone(n, this.getID());
            boolean bl = this.useDaylightTime();
            if (bl) {
                Object object;
                objectArray = this.getSimpleTimeZoneRulesNear(l);
                if (objectArray.length != 3 && (object = this.getPreviousTransition(l, true)) != null) {
                    objectArray = this.getSimpleTimeZoneRulesNear(((TimeZoneTransition)object).getTime() - 1L);
                }
                if (objectArray.length == 3 && objectArray[0] instanceof AnnualTimeZoneRule && objectArray[5] instanceof AnnualTimeZoneRule) {
                    int n2;
                    DateTimeRule dateTimeRule;
                    DateTimeRule dateTimeRule2;
                    int n3;
                    object = (AnnualTimeZoneRule)objectArray[0];
                    AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)objectArray[5];
                    int n4 = ((TimeZoneRule)object).getRawOffset() + ((TimeZoneRule)object).getDSTSavings();
                    if (n4 > (n3 = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings())) {
                        dateTimeRule2 = ((AnnualTimeZoneRule)object).getRule();
                        dateTimeRule = annualTimeZoneRule.getRule();
                        n2 = n4 - n3;
                    } else {
                        dateTimeRule2 = annualTimeZoneRule.getRule();
                        dateTimeRule = ((AnnualTimeZoneRule)object).getRule();
                        n2 = n3 - n4;
                    }
                    simpleTimeZone.setStartRule(dateTimeRule2.getRuleMonth(), dateTimeRule2.getRuleWeekInMonth(), dateTimeRule2.getRuleDayOfWeek(), dateTimeRule2.getRuleMillisInDay());
                    simpleTimeZone.setEndRule(dateTimeRule.getRuleMonth(), dateTimeRule.getRuleWeekInMonth(), dateTimeRule.getRuleDayOfWeek(), dateTimeRule.getRuleMillisInDay());
                    simpleTimeZone.setDSTSavings(n2);
                } else {
                    simpleTimeZone.setStartRule(0, 1, 0);
                    simpleTimeZone.setEndRule(11, 31, 86399999);
                }
            }
            objectArray = Grego.timeToFields(l, null);
            this.finalStartYear = (int)objectArray[0];
            this.finalStartMillis = Grego.fieldsToDay((int)objectArray[0], 0, 1);
            if (bl) {
                simpleTimeZone.setStartYear(this.finalStartYear);
            }
            this.finalZone = simpleTimeZone;
        } else {
            this.finalZone.setRawOffset(n);
        }
        this.transitionRulesInitialized = false;
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public void getOffset(long l, boolean bl, int[] nArray) {
        if (this.finalZone != null && (double)l >= this.finalStartMillis) {
            this.finalZone.getOffset(l, bl, nArray);
        } else {
            this.getHistoricalOffset(l, bl, 4, 12, nArray);
        }
    }

    @Override
    public void getOffsetFromLocal(long l, int n, int n2, int[] nArray) {
        if (this.finalZone != null && (double)l >= this.finalStartMillis) {
            this.finalZone.getOffsetFromLocal(l, n, n2, nArray);
        } else {
            this.getHistoricalOffset(l, true, n, n2, nArray);
        }
    }

    @Override
    public int getRawOffset() {
        int[] nArray = new int[2];
        this.getOffset(System.currentTimeMillis(), false, nArray);
        return nArray[0];
    }

    @Override
    public boolean useDaylightTime() {
        long l = System.currentTimeMillis();
        if (this.finalZone != null && (double)l >= this.finalStartMillis) {
            return this.finalZone != null && this.finalZone.useDaylightTime();
        }
        int[] nArray = Grego.timeToFields(l, null);
        long l2 = Grego.fieldsToDay(nArray[0], 0, 1) * 86400L;
        long l3 = Grego.fieldsToDay(nArray[0] + 1, 0, 1) * 86400L;
        for (int i = 0; i < this.transitionCount && this.transitionTimes64[i] < l3; ++i) {
            if ((this.transitionTimes64[i] < l2 || this.dstOffsetAt(i) == 0) && (this.transitionTimes64[i] <= l2 || i <= 0 || this.dstOffsetAt(i - 1) == 0)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean observesDaylightTime() {
        long l = System.currentTimeMillis();
        if (this.finalZone != null) {
            if (this.finalZone.useDaylightTime()) {
                return false;
            }
            if ((double)l >= this.finalStartMillis) {
                return true;
            }
        }
        long l2 = Grego.floorDivide(l, 1000L);
        int n = this.transitionCount - 1;
        if (this.dstOffsetAt(n) != 0) {
            return false;
        }
        while (n >= 0 && this.transitionTimes64[n] > l2) {
            if (this.dstOffsetAt(n - 1) != 0) {
                return false;
            }
            --n;
        }
        return true;
    }

    @Override
    public int getDSTSavings() {
        if (this.finalZone != null) {
            return this.finalZone.getDSTSavings();
        }
        return super.getDSTSavings();
    }

    @Override
    public boolean inDaylightTime(Date date) {
        int[] nArray = new int[2];
        this.getOffset(date.getTime(), false, nArray);
        return nArray[1] != 0;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return false;
        }
        if (!super.hasSameRules(timeZone)) {
            return true;
        }
        if (!(timeZone instanceof OlsonTimeZone)) {
            return true;
        }
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone)timeZone;
        if (this.finalZone == null ? olsonTimeZone.finalZone != null : olsonTimeZone.finalZone == null || this.finalStartYear != olsonTimeZone.finalStartYear || !this.finalZone.hasSameRules(olsonTimeZone.finalZone)) {
            return true;
        }
        return this.transitionCount != olsonTimeZone.transitionCount || !Arrays.equals(this.transitionTimes64, olsonTimeZone.transitionTimes64) || this.typeCount != olsonTimeZone.typeCount || !Arrays.equals(this.typeMapData, olsonTimeZone.typeMapData) || !Arrays.equals(this.typeOffsets, olsonTimeZone.typeOffsets);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getCanonicalID() {
        if (this.canonicalID == null) {
            OlsonTimeZone olsonTimeZone = this;
            synchronized (olsonTimeZone) {
                if (this.canonicalID == null) {
                    this.canonicalID = OlsonTimeZone.getCanonicalID(this.getID());
                    if (!$assertionsDisabled && this.canonicalID == null) {
                        throw new AssertionError();
                    }
                    if (this.canonicalID == null) {
                        this.canonicalID = this.getID();
                    }
                }
            }
        }
        return this.canonicalID;
    }

    private void constructEmpty() {
        this.transitionCount = 0;
        this.transitionTimes64 = null;
        this.typeMapData = null;
        this.typeCount = 1;
        this.typeOffsets = new int[]{0, 0};
        this.finalZone = null;
        this.finalStartYear = Integer.MAX_VALUE;
        this.finalStartMillis = Double.MAX_VALUE;
        this.transitionRulesInitialized = false;
    }

    public OlsonTimeZone(UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2, String string) {
        super(string);
        this.construct(uResourceBundle, uResourceBundle2, string);
    }

    private void construct(UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2, String string) {
        block25: {
            int n;
            UResourceBundle uResourceBundle3;
            if (uResourceBundle == null || uResourceBundle2 == null) {
                throw new IllegalArgumentException();
            }
            if (DEBUG) {
                System.out.println("OlsonTimeZone(" + uResourceBundle2.getKey() + ")");
            }
            int[] nArray = null;
            int[] nArray2 = null;
            int[] nArray3 = null;
            this.transitionCount = 0;
            try {
                uResourceBundle3 = uResourceBundle2.get("transPre32");
                nArray3 = uResourceBundle3.getIntVector();
                if (nArray3.length % 2 != 0) {
                    throw new IllegalArgumentException("Invalid Format");
                }
                this.transitionCount += nArray3.length / 2;
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                uResourceBundle3 = uResourceBundle2.get("trans");
                nArray2 = uResourceBundle3.getIntVector();
                this.transitionCount += nArray2.length;
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                uResourceBundle3 = uResourceBundle2.get("transPost32");
                nArray = uResourceBundle3.getIntVector();
                if (nArray.length % 2 != 0) {
                    throw new IllegalArgumentException("Invalid Format");
                }
                this.transitionCount += nArray.length / 2;
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (this.transitionCount > 0) {
                this.transitionTimes64 = new long[this.transitionCount];
                int n2 = 0;
                if (nArray3 != null) {
                    n = 0;
                    while (n < nArray3.length / 2) {
                        this.transitionTimes64[n2] = ((long)nArray3[n * 2] & 0xFFFFFFFFL) << 32 | (long)nArray3[n * 2 + 1] & 0xFFFFFFFFL;
                        ++n;
                        ++n2;
                    }
                }
                if (nArray2 != null) {
                    n = 0;
                    while (n < nArray2.length) {
                        this.transitionTimes64[n2] = nArray2[n];
                        ++n;
                        ++n2;
                    }
                }
                if (nArray != null) {
                    n = 0;
                    while (n < nArray.length / 2) {
                        this.transitionTimes64[n2] = ((long)nArray[n * 2] & 0xFFFFFFFFL) << 32 | (long)nArray[n * 2 + 1] & 0xFFFFFFFFL;
                        ++n;
                        ++n2;
                    }
                }
            } else {
                this.transitionTimes64 = null;
            }
            uResourceBundle3 = uResourceBundle2.get("typeOffsets");
            this.typeOffsets = uResourceBundle3.getIntVector();
            if (this.typeOffsets.length < 2 || this.typeOffsets.length > 32766 || this.typeOffsets.length % 2 != 0) {
                throw new IllegalArgumentException("Invalid Format");
            }
            this.typeCount = this.typeOffsets.length / 2;
            if (this.transitionCount > 0) {
                uResourceBundle3 = uResourceBundle2.get("typeMap");
                this.typeMapData = uResourceBundle3.getBinary(null);
                if (this.typeMapData == null || this.typeMapData.length != this.transitionCount) {
                    throw new IllegalArgumentException("Invalid Format");
                }
            } else {
                this.typeMapData = null;
            }
            this.finalZone = null;
            this.finalStartYear = Integer.MAX_VALUE;
            this.finalStartMillis = Double.MAX_VALUE;
            String string2 = null;
            try {
                string2 = uResourceBundle2.getString("finalRule");
                uResourceBundle3 = uResourceBundle2.get("finalRaw");
                n = uResourceBundle3.getInt() * 1000;
                uResourceBundle3 = OlsonTimeZone.loadRule(uResourceBundle, string2);
                int[] nArray4 = uResourceBundle3.getIntVector();
                if (nArray4 == null || nArray4.length != 11) {
                    throw new IllegalArgumentException("Invalid Format");
                }
                this.finalZone = new SimpleTimeZone(n, string, nArray4[0], nArray4[1], nArray4[2], nArray4[3] * 1000, nArray4[4], nArray4[5], nArray4[6], nArray4[7], nArray4[8] * 1000, nArray4[9], nArray4[10] * 1000);
                uResourceBundle3 = uResourceBundle2.get("finalYear");
                this.finalStartYear = uResourceBundle3.getInt();
                this.finalStartMillis = Grego.fieldsToDay(this.finalStartYear, 0, 1) * 86400000L;
            } catch (MissingResourceException missingResourceException) {
                if (string2 == null) break block25;
                throw new IllegalArgumentException("Invalid Format");
            }
        }
    }

    public OlsonTimeZone(String string) {
        super(string);
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORES, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = ZoneMeta.openOlsonResource(uResourceBundle, string);
        this.construct(uResourceBundle, uResourceBundle2, string);
    }

    @Override
    public void setID(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen OlsonTimeZone instance.");
        }
        if (this.canonicalID == null) {
            this.canonicalID = OlsonTimeZone.getCanonicalID(this.getID());
            if (!$assertionsDisabled && this.canonicalID == null) {
                throw new AssertionError();
            }
            if (this.canonicalID == null) {
                this.canonicalID = this.getID();
            }
        }
        if (this.finalZone != null) {
            this.finalZone.setID(string);
        }
        super.setID(string);
        this.transitionRulesInitialized = false;
    }

    private void getHistoricalOffset(long l, boolean bl, int n, int n2, int[] nArray) {
        if (this.transitionCount != 0) {
            long l2 = Grego.floorDivide(l, 1000L);
            if (!bl && l2 < this.transitionTimes64[0]) {
                nArray[0] = this.initialRawOffset() * 1000;
                nArray[1] = this.initialDstOffset() * 1000;
            } else {
                int n3;
                for (n3 = this.transitionCount - 1; n3 >= 0; --n3) {
                    long l3 = this.transitionTimes64[n3];
                    if (bl && l2 >= l3 - 86400L) {
                        boolean bl2;
                        int n4 = this.zoneOffsetAt(n3 - 1);
                        boolean bl3 = this.dstOffsetAt(n3 - 1) != 0;
                        int n5 = this.zoneOffsetAt(n3);
                        boolean bl4 = this.dstOffsetAt(n3) != 0;
                        boolean bl5 = bl3 && !bl4;
                        boolean bl6 = bl2 = !bl3 && bl4;
                        l3 = n5 - n4 >= 0 ? ((n & 3) == 1 && bl5 || (n & 3) == 3 && bl2 ? (l3 += (long)n4) : ((n & 3) == 1 && bl2 || (n & 3) == 3 && bl5 ? (l3 += (long)n5) : ((n & 0xC) == 12 ? (l3 += (long)n4) : (l3 += (long)n5)))) : ((n2 & 3) == 1 && bl5 || (n2 & 3) == 3 && bl2 ? (l3 += (long)n5) : ((n2 & 3) == 1 && bl2 || (n2 & 3) == 3 && bl5 ? (l3 += (long)n4) : ((n2 & 0xC) == 4 ? (l3 += (long)n4) : (l3 += (long)n5))));
                    }
                    if (l2 >= l3) break;
                }
                nArray[0] = this.rawOffsetAt(n3) * 1000;
                nArray[1] = this.dstOffsetAt(n3) * 1000;
            }
        } else {
            nArray[0] = this.initialRawOffset() * 1000;
            nArray[1] = this.initialDstOffset() * 1000;
        }
    }

    private int getInt(byte by) {
        return by & 0xFF;
    }

    private int zoneOffsetAt(int n) {
        int n2 = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        return this.typeOffsets[n2] + this.typeOffsets[n2 + 1];
    }

    private int rawOffsetAt(int n) {
        int n2 = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        return this.typeOffsets[n2];
    }

    private int dstOffsetAt(int n) {
        int n2 = n >= 0 ? this.getInt(this.typeMapData[n]) * 2 : 0;
        return this.typeOffsets[n2 + 1];
    }

    private int initialRawOffset() {
        return this.typeOffsets[0];
    }

    private int initialDstOffset() {
        return this.typeOffsets[1];
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append('[');
        stringBuilder.append("transitionCount=" + this.transitionCount);
        stringBuilder.append(",typeCount=" + this.typeCount);
        stringBuilder.append(",transitionTimes=");
        if (this.transitionTimes64 != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.transitionTimes64.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Long.toString(this.transitionTimes64[n]));
            }
            stringBuilder.append(']');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(",typeOffsets=");
        if (this.typeOffsets != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.typeOffsets.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Integer.toString(this.typeOffsets[n]));
            }
            stringBuilder.append(']');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(",typeMapData=");
        if (this.typeMapData != null) {
            stringBuilder.append('[');
            for (n = 0; n < this.typeMapData.length; ++n) {
                if (n > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(Byte.toString(this.typeMapData[n]));
            }
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(",finalStartYear=" + this.finalStartYear);
        stringBuilder.append(",finalStartMillis=" + this.finalStartMillis);
        stringBuilder.append(",finalZone=" + this.finalZone);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static UResourceBundle loadRule(UResourceBundle uResourceBundle, String string) {
        UResourceBundle uResourceBundle2 = uResourceBundle.get("Rules");
        uResourceBundle2 = uResourceBundle2.get(string);
        return uResourceBundle2;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return true;
        }
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone)object;
        return Utility.arrayEquals(this.typeMapData, (Object)olsonTimeZone.typeMapData) || this.finalStartYear == olsonTimeZone.finalStartYear && (this.finalZone == null && olsonTimeZone.finalZone == null || this.finalZone != null && olsonTimeZone.finalZone != null && this.finalZone.equals(olsonTimeZone.finalZone) && this.transitionCount == olsonTimeZone.transitionCount && this.typeCount == olsonTimeZone.typeCount && Utility.arrayEquals((Object)this.transitionTimes64, (Object)olsonTimeZone.transitionTimes64) && Utility.arrayEquals(this.typeOffsets, (Object)olsonTimeZone.typeOffsets) && Utility.arrayEquals(this.typeMapData, (Object)olsonTimeZone.typeMapData));
    }

    @Override
    public int hashCode() {
        int n;
        int n2 = (int)((long)(this.finalStartYear ^ (this.finalStartYear >>> 4) + this.transitionCount ^ (this.transitionCount >>> 6) + this.typeCount) ^ (long)(this.typeCount >>> 8) + Double.doubleToLongBits(this.finalStartMillis) + (long)(this.finalZone == null ? 0 : this.finalZone.hashCode()) + (long)super.hashCode());
        if (this.transitionTimes64 != null) {
            for (n = 0; n < this.transitionTimes64.length; ++n) {
                n2 = (int)((long)n2 + (this.transitionTimes64[n] ^ this.transitionTimes64[n] >>> 8));
            }
        }
        for (n = 0; n < this.typeOffsets.length; ++n) {
            n2 += this.typeOffsets[n] ^ this.typeOffsets[n] >>> 8;
        }
        if (this.typeMapData != null) {
            for (n = 0; n < this.typeMapData.length; ++n) {
                n2 += this.typeMapData[n] & 0xFF;
            }
        }
        return n2;
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (bl && l == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (l >= this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getNextTransition(l, bl);
                }
                return null;
            }
        }
        if (this.historicRules != null) {
            long l2;
            int n;
            for (n = this.transitionCount - 1; n >= this.firstTZTransitionIdx && l <= (l2 = this.transitionTimes64[n] * 1000L) && (bl || l != l2); --n) {
            }
            if (n == this.transitionCount - 1) {
                return this.firstFinalTZTransition;
            }
            if (n < this.firstTZTransitionIdx) {
                return this.firstTZTransition;
            }
            TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[n + 1])];
            TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[n])];
            long l3 = this.transitionTimes64[n + 1] * 1000L;
            if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
                return this.getNextTransition(l3, true);
            }
            return new TimeZoneTransition(l3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
        }
        return null;
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        this.initTransitionRules();
        if (this.finalZone != null) {
            if (bl && l == this.firstFinalTZTransition.getTime()) {
                return this.firstFinalTZTransition;
            }
            if (l > this.firstFinalTZTransition.getTime()) {
                if (this.finalZone.useDaylightTime()) {
                    return this.finalZoneWithStartYear.getPreviousTransition(l, bl);
                }
                return this.firstFinalTZTransition;
            }
        }
        if (this.historicRules != null) {
            long l2;
            int n;
            for (n = this.transitionCount - 1; !(n < this.firstTZTransitionIdx || l > (l2 = this.transitionTimes64[n] * 1000L) || bl && l == l2); --n) {
            }
            if (n < this.firstTZTransitionIdx) {
                return null;
            }
            if (n == this.firstTZTransitionIdx) {
                return this.firstTZTransition;
            }
            TimeArrayTimeZoneRule timeArrayTimeZoneRule = this.historicRules[this.getInt(this.typeMapData[n])];
            TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = this.historicRules[this.getInt(this.typeMapData[n - 1])];
            long l3 = this.transitionTimes64[n] * 1000L;
            if (timeArrayTimeZoneRule2.getName().equals(timeArrayTimeZoneRule.getName()) && timeArrayTimeZoneRule2.getRawOffset() == timeArrayTimeZoneRule.getRawOffset() && timeArrayTimeZoneRule2.getDSTSavings() == timeArrayTimeZoneRule.getDSTSavings()) {
                return this.getPreviousTransition(l3, true);
            }
            return new TimeZoneTransition(l3, timeArrayTimeZoneRule2, timeArrayTimeZoneRule);
        }
        return null;
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        this.initTransitionRules();
        int n = 1;
        if (this.historicRules != null) {
            for (int i = 0; i < this.historicRules.length; ++i) {
                if (this.historicRules[i] == null) continue;
                ++n;
            }
        }
        if (this.finalZone != null) {
            n = this.finalZone.useDaylightTime() ? (n += 2) : ++n;
        }
        TimeZoneRule[] timeZoneRuleArray = new TimeZoneRule[n];
        int n2 = 0;
        timeZoneRuleArray[n2++] = this.initialRule;
        if (this.historicRules != null) {
            for (int i = 0; i < this.historicRules.length; ++i) {
                if (this.historicRules[i] == null) continue;
                timeZoneRuleArray[n2++] = this.historicRules[i];
            }
        }
        if (this.finalZone != null) {
            if (this.finalZone.useDaylightTime()) {
                TimeZoneRule[] timeZoneRuleArray2 = this.finalZoneWithStartYear.getTimeZoneRules();
                timeZoneRuleArray[n2++] = timeZoneRuleArray2[0];
                timeZoneRuleArray[n2++] = timeZoneRuleArray2[5];
            } else {
                timeZoneRuleArray[n2++] = new TimeArrayTimeZoneRule(this.getID() + "(STD)", this.finalZone.getRawOffset(), 0, new long[]{(long)this.finalStartMillis}, 2);
            }
        }
        return timeZoneRuleArray;
    }

    private synchronized void initTransitionRules() {
        Object object;
        if (this.transitionRulesInitialized) {
            return;
        }
        this.initialRule = null;
        this.firstTZTransition = null;
        this.firstFinalTZTransition = null;
        this.historicRules = null;
        this.firstTZTransitionIdx = 0;
        this.finalZoneWithStartYear = null;
        String string = this.getID() + "(STD)";
        String string2 = this.getID() + "(DST)";
        int n = this.initialRawOffset() * 1000;
        int n2 = this.initialDstOffset() * 1000;
        this.initialRule = new InitialTimeZoneRule(n2 == 0 ? string : string2, n, n2);
        if (this.transitionCount > 0) {
            int n3;
            for (n3 = 0; n3 < this.transitionCount && this.getInt(this.typeMapData[n3]) == 0; ++n3) {
                ++this.firstTZTransitionIdx;
            }
            if (n3 != this.transitionCount) {
                int n4;
                object = new long[this.transitionCount];
                for (n4 = 0; n4 < this.typeCount; ++n4) {
                    int n5 = 0;
                    for (n3 = this.firstTZTransitionIdx; n3 < this.transitionCount; ++n3) {
                        long l;
                        if (n4 != this.getInt(this.typeMapData[n3]) || !((double)(l = this.transitionTimes64[n3] * 1000L) < this.finalStartMillis)) continue;
                        object[n5++] = l;
                    }
                    if (n5 <= 0) continue;
                    long[] lArray = new long[n5];
                    System.arraycopy(object, 0, lArray, 0, n5);
                    n = this.typeOffsets[n4 * 2] * 1000;
                    n2 = this.typeOffsets[n4 * 2 + 1] * 1000;
                    if (this.historicRules == null) {
                        this.historicRules = new TimeArrayTimeZoneRule[this.typeCount];
                    }
                    this.historicRules[n4] = new TimeArrayTimeZoneRule(n2 == 0 ? string : string2, n, n2, lArray, 2);
                }
                n4 = this.getInt(this.typeMapData[this.firstTZTransitionIdx]);
                this.firstTZTransition = new TimeZoneTransition(this.transitionTimes64[this.firstTZTransitionIdx] * 1000L, this.initialRule, this.historicRules[n4]);
            }
        }
        if (this.finalZone != null) {
            long l = (long)this.finalStartMillis;
            if (this.finalZone.useDaylightTime()) {
                this.finalZoneWithStartYear = (SimpleTimeZone)this.finalZone.clone();
                this.finalZoneWithStartYear.setStartYear(this.finalStartYear);
                TimeZoneTransition timeZoneTransition = this.finalZoneWithStartYear.getNextTransition(l, true);
                object = timeZoneTransition.getTo();
                l = timeZoneTransition.getTime();
            } else {
                this.finalZoneWithStartYear = this.finalZone;
                object = new TimeArrayTimeZoneRule(this.finalZone.getID(), this.finalZone.getRawOffset(), 0, new long[]{l}, 2);
            }
            TimeZoneRule timeZoneRule = null;
            if (this.transitionCount > 0) {
                timeZoneRule = this.historicRules[this.getInt(this.typeMapData[this.transitionCount - 1])];
            }
            if (timeZoneRule == null) {
                timeZoneRule = this.initialRule;
            }
            this.firstFinalTZTransition = new TimeZoneTransition(l, timeZoneRule, (TimeZoneRule)object);
        }
        this.transitionRulesInitialized = true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            boolean bl = false;
            String string = this.getID();
            if (string != null) {
                try {
                    UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORES, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                    UResourceBundle uResourceBundle2 = ZoneMeta.openOlsonResource(uResourceBundle, string);
                    this.construct(uResourceBundle, uResourceBundle2, string);
                    bl = true;
                } catch (Exception exception) {
                    // empty catch block
                }
            }
            if (!bl) {
                this.constructEmpty();
            }
        }
        this.transitionRulesInitialized = false;
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override
    public TimeZone cloneAsThawed() {
        OlsonTimeZone olsonTimeZone = (OlsonTimeZone)super.cloneAsThawed();
        if (this.finalZone != null) {
            olsonTimeZone.finalZone = (SimpleTimeZone)this.finalZone.clone();
        }
        olsonTimeZone.isFrozen = false;
        return olsonTimeZone;
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
        $assertionsDisabled = !OlsonTimeZone.class.desiredAssertionStatus();
        DEBUG = ICUDebug.enabled("olson");
    }
}

