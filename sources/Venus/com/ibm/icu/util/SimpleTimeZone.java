/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.AnnualTimeZoneRule;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.STZInfo;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.TimeZoneRule;
import com.ibm.icu.util.TimeZoneTransition;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SimpleTimeZone
extends BasicTimeZone {
    private static final long serialVersionUID = -7034676239311322769L;
    public static final int WALL_TIME = 0;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    private static final byte[] staticMonthLength;
    private static final int DOM_MODE = 1;
    private static final int DOW_IN_MONTH_MODE = 2;
    private static final int DOW_GE_DOM_MODE = 3;
    private static final int DOW_LE_DOM_MODE = 4;
    private int raw;
    private int dst = 3600000;
    private STZInfo xinfo = null;
    private int startMonth;
    private int startDay;
    private int startDayOfWeek;
    private int startTime;
    private int startTimeMode;
    private int endTimeMode;
    private int endMonth;
    private int endDay;
    private int endDayOfWeek;
    private int endTime;
    private int startYear;
    private boolean useDaylight;
    private int startMode;
    private int endMode;
    private transient boolean transitionRulesInitialized;
    private transient InitialTimeZoneRule initialRule;
    private transient TimeZoneTransition firstTransition;
    private transient AnnualTimeZoneRule stdRule;
    private transient AnnualTimeZoneRule dstRule;
    private volatile transient boolean isFrozen = false;
    static final boolean $assertionsDisabled;

    public SimpleTimeZone(int n, String string) {
        super(string);
        this.construct(n, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3600000);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        super(string);
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, 3600000);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        super(string);
        this.construct(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        super(string);
        this.construct(n, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, n10);
    }

    @Override
    public void setID(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        super.setID(string);
        this.transitionRulesInitialized = false;
    }

    @Override
    public void setRawOffset(int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.raw = n;
        this.transitionRulesInitialized = false;
    }

    @Override
    public int getRawOffset() {
        return this.raw;
    }

    public void setStartYear(int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().sy = n;
        this.startYear = n;
        this.transitionRulesInitialized = false;
    }

    public void setStartRule(int n, int n2, int n3, int n4) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, n2, n3, n4, -1, true);
        this.setStartRule(n, n2, n3, n4, 0);
    }

    private void setStartRule(int n, int n2, int n3, int n4, int n5) {
        if (!$assertionsDisabled && this.isFrozen()) {
            throw new AssertionError();
        }
        this.startMonth = n;
        this.startDay = n2;
        this.startDayOfWeek = n3;
        this.startTime = n4;
        this.startTimeMode = n5;
        this.decodeStartRule();
        this.transitionRulesInitialized = false;
    }

    public void setStartRule(int n, int n2, int n3) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, -1, -1, n3, n2, true);
        this.setStartRule(n, n2, 0, n3, 0);
    }

    public void setStartRule(int n, int n2, int n3, int n4, boolean bl) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setStart(n, -1, n3, n4, n2, bl);
        this.setStartRule(n, bl ? n2 : -n2, -n3, n4, 0);
    }

    public void setEndRule(int n, int n2, int n3, int n4) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, n2, n3, n4, -1, true);
        this.setEndRule(n, n2, n3, n4, 0);
    }

    public void setEndRule(int n, int n2, int n3) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, -1, -1, n3, n2, true);
        this.setEndRule(n, n2, 0, n3);
    }

    public void setEndRule(int n, int n2, int n3, int n4, boolean bl) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        this.getSTZInfo().setEnd(n, -1, n3, n4, n2, bl);
        this.setEndRule(n, n2, n3, n4, 0, bl);
    }

    private void setEndRule(int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (!$assertionsDisabled && this.isFrozen()) {
            throw new AssertionError();
        }
        this.setEndRule(n, bl ? n2 : -n2, -n3, n4, n5);
    }

    private void setEndRule(int n, int n2, int n3, int n4, int n5) {
        if (!$assertionsDisabled && this.isFrozen()) {
            throw new AssertionError();
        }
        this.endMonth = n;
        this.endDay = n2;
        this.endDayOfWeek = n3;
        this.endTime = n4;
        this.endTimeMode = n5;
        this.decodeEndRule();
        this.transitionRulesInitialized = false;
    }

    public void setDSTSavings(int n) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance.");
        }
        if (n == 0) {
            throw new IllegalArgumentException();
        }
        this.dst = n;
        this.transitionRulesInitialized = false;
    }

    @Override
    public int getDSTSavings() {
        return this.dst;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.xinfo != null) {
            this.xinfo.applyTo(this);
        }
    }

    public String toString() {
        return "SimpleTimeZone: " + this.getID();
    }

    private STZInfo getSTZInfo() {
        if (this.xinfo == null) {
            this.xinfo = new STZInfo();
        }
        return this.xinfo;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException();
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3));
    }

    @Deprecated
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n3 < 0 || n3 > 11) {
            throw new IllegalArgumentException();
        }
        return this.getOffset(n, n2, n3, n4, n5, n6, Grego.monthLength(n2, n3), Grego.previousMonthLength(n2, n3));
    }

    private int getOffset(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (n != 1 && n != 0 || n3 < 0 || n3 > 11 || n4 < 1 || n4 > n7 || n5 < 1 || n5 > 7 || n6 < 0 || n6 >= 86400000 || n7 < 28 || n7 > 31 || n8 < 28 || n8 > 31) {
            throw new IllegalArgumentException();
        }
        int n9 = this.raw;
        if (!this.useDaylight || n2 < this.startYear || n != 1) {
            return n9;
        }
        boolean bl = this.startMonth > this.endMonth;
        int n10 = this.compareToRule(n3, n7, n8, n4, n5, n6, this.startTimeMode == 2 ? -this.raw : 0, this.startMode, this.startMonth, this.startDayOfWeek, this.startDay, this.startTime);
        int n11 = 0;
        if (bl != n10 >= 0) {
            n11 = this.compareToRule(n3, n7, n8, n4, n5, n6, this.endTimeMode == 0 ? this.dst : (this.endTimeMode == 2 ? -this.raw : 0), this.endMode, this.endMonth, this.endDayOfWeek, this.endDay, this.endTime);
        }
        if (!bl && n10 >= 0 && n11 < 0 || bl && (n10 >= 0 || n11 < 0)) {
            n9 += this.dst;
        }
        return n9;
    }

    @Override
    @Deprecated
    public void getOffsetFromLocal(long l, int n, int n2, int[] nArray) {
        nArray[0] = this.getRawOffset();
        int[] nArray2 = new int[6];
        Grego.timeToFields(l, nArray2);
        nArray[1] = this.getOffset(1, nArray2[0], nArray2[1], nArray2[2], nArray2[3], nArray2[5]) - nArray[0];
        boolean bl = false;
        if (nArray[1] > 0) {
            if ((n & 3) == 1 || (n & 3) != 3 && (n & 0xC) != 12) {
                l -= (long)this.getDSTSavings();
                bl = true;
            }
        } else if ((n2 & 3) == 3 || (n2 & 3) != 1 && (n2 & 0xC) == 4) {
            l -= (long)this.getDSTSavings();
            bl = true;
        }
        if (bl) {
            Grego.timeToFields(l, nArray2);
            nArray[1] = this.getOffset(1, nArray2[0], nArray2[1], nArray2[2], nArray2[3], nArray2[5]) - nArray[0];
        }
    }

    private int compareToRule(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        n6 += n7;
        while (n6 >= 86400000) {
            n6 -= 86400000;
            n5 = 1 + n5 % 7;
            if (++n4 <= n2) continue;
            n4 = 1;
            ++n;
        }
        while (n6 < 0) {
            n5 = 1 + (n5 + 5) % 7;
            if (--n4 < 1) {
                n4 = n3;
                --n;
            }
            n6 += 86400000;
        }
        if (n < n9) {
            return 1;
        }
        if (n > n9) {
            return 0;
        }
        int n13 = 0;
        if (n11 > n2) {
            n11 = n2;
        }
        switch (n8) {
            case 1: {
                n13 = n11;
                break;
            }
            case 2: {
                if (n11 > 0) {
                    n13 = 1 + (n11 - 1) * 7 + (7 + n10 - (n5 - n4 + 1)) % 7;
                    break;
                }
                n13 = n2 + (n11 + 1) * 7 - (7 + (n5 + n2 - n4) - n10) % 7;
                break;
            }
            case 3: {
                n13 = n11 + (49 + n10 - n11 - n5 + n4) % 7;
                break;
            }
            case 4: {
                n13 = n11 - (49 - n10 + n11 + n5 - n4) % 7;
            }
        }
        if (n4 < n13) {
            return 1;
        }
        if (n4 > n13) {
            return 0;
        }
        if (n6 < n12) {
            return 1;
        }
        if (n6 > n12) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean useDaylightTime() {
        return this.useDaylight;
    }

    @Override
    public boolean observesDaylightTime() {
        return this.useDaylight;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this);
        gregorianCalendar.setTime(date);
        return gregorianCalendar.inDaylightTime();
    }

    private void construct(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        this.raw = n;
        this.startMonth = n2;
        this.startDay = n3;
        this.startDayOfWeek = n4;
        this.startTime = n5;
        this.startTimeMode = n6;
        this.endMonth = n7;
        this.endDay = n8;
        this.endDayOfWeek = n9;
        this.endTime = n10;
        this.endTimeMode = n11;
        this.dst = n12;
        this.startYear = 0;
        this.startMode = 1;
        this.endMode = 1;
        this.decodeRules();
        if (n12 == 0) {
            throw new IllegalArgumentException();
        }
    }

    private void decodeRules() {
        this.decodeStartRule();
        this.decodeEndRule();
    }

    private void decodeStartRule() {
        boolean bl = this.useDaylight = this.startDay != 0 && this.endDay != 0;
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if (this.startDay != 0) {
            if (this.startMonth < 0 || this.startMonth > 11) {
                throw new IllegalArgumentException();
            }
            if (this.startTime < 0 || this.startTime > 86400000 || this.startTimeMode < 0 || this.startTimeMode > 2) {
                throw new IllegalArgumentException();
            }
            if (this.startDayOfWeek == 0) {
                this.startMode = 1;
            } else {
                if (this.startDayOfWeek > 0) {
                    this.startMode = 2;
                } else {
                    this.startDayOfWeek = -this.startDayOfWeek;
                    if (this.startDay > 0) {
                        this.startMode = 3;
                    } else {
                        this.startDay = -this.startDay;
                        this.startMode = 4;
                    }
                }
                if (this.startDayOfWeek > 7) {
                    throw new IllegalArgumentException();
                }
            }
            if (this.startMode == 2 ? this.startDay < -5 || this.startDay > 5 : this.startDay < 1 || this.startDay > staticMonthLength[this.startMonth]) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void decodeEndRule() {
        boolean bl = this.useDaylight = this.startDay != 0 && this.endDay != 0;
        if (this.useDaylight && this.dst == 0) {
            this.dst = 86400000;
        }
        if (this.endDay != 0) {
            if (this.endMonth < 0 || this.endMonth > 11) {
                throw new IllegalArgumentException();
            }
            if (this.endTime < 0 || this.endTime > 86400000 || this.endTimeMode < 0 || this.endTimeMode > 2) {
                throw new IllegalArgumentException();
            }
            if (this.endDayOfWeek == 0) {
                this.endMode = 1;
            } else {
                if (this.endDayOfWeek > 0) {
                    this.endMode = 2;
                } else {
                    this.endDayOfWeek = -this.endDayOfWeek;
                    if (this.endDay > 0) {
                        this.endMode = 3;
                    } else {
                        this.endDay = -this.endDay;
                        this.endMode = 4;
                    }
                }
                if (this.endDayOfWeek > 7) {
                    throw new IllegalArgumentException();
                }
            }
            if (this.endMode == 2 ? this.endDay < -5 || this.endDay > 5 : this.endDay < 1 || this.endDay > staticMonthLength[this.endMonth]) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SimpleTimeZone simpleTimeZone = (SimpleTimeZone)object;
        return this.raw == simpleTimeZone.raw && this.useDaylight == simpleTimeZone.useDaylight && this.idEquals(this.getID(), simpleTimeZone.getID()) && (!this.useDaylight || this.dst == simpleTimeZone.dst && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear);
    }

    private boolean idEquals(String string, String string2) {
        if (string == null && string2 == null) {
            return false;
        }
        if (string != null && string2 != null) {
            return string.equals(string2);
        }
        return true;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode() + this.raw ^ (this.raw >>> 8) + (this.useDaylight ? 0 : 1);
        if (!this.useDaylight) {
            n += this.dst ^ (this.dst >>> 10) + this.startMode ^ (this.startMode >>> 11) + this.startMonth ^ (this.startMonth >>> 12) + this.startDay ^ (this.startDay >>> 13) + this.startDayOfWeek ^ (this.startDayOfWeek >>> 14) + this.startTime ^ (this.startTime >>> 15) + this.startTimeMode ^ (this.startTimeMode >>> 16) + this.endMode ^ (this.endMode >>> 17) + this.endMonth ^ (this.endMonth >>> 18) + this.endDay ^ (this.endDay >>> 19) + this.endDayOfWeek ^ (this.endDayOfWeek >>> 20) + this.endTime ^ (this.endTime >>> 21) + this.endTimeMode ^ (this.endTimeMode >>> 22) + this.startYear ^ this.startYear >>> 23;
        }
        return n;
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return false;
        }
        if (!(timeZone instanceof SimpleTimeZone)) {
            return true;
        }
        SimpleTimeZone simpleTimeZone = (SimpleTimeZone)timeZone;
        return simpleTimeZone != null && this.raw == simpleTimeZone.raw && this.useDaylight == simpleTimeZone.useDaylight && (!this.useDaylight || this.dst == simpleTimeZone.dst && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear);
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        long l2 = this.firstTransition.getTime();
        if (l < l2 || bl && l == l2) {
            return this.firstTransition;
        }
        Date date = this.stdRule.getNextStart(l, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), bl);
        Date date2 = this.dstRule.getNextStart(l, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), bl);
        if (date != null && (date2 == null || date.before(date2))) {
            return new TimeZoneTransition(date.getTime(), this.dstRule, this.stdRule);
        }
        if (date2 != null && (date == null || date2.before(date))) {
            return new TimeZoneTransition(date2.getTime(), this.stdRule, this.dstRule);
        }
        return null;
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        if (!this.useDaylight) {
            return null;
        }
        this.initTransitionRules();
        long l2 = this.firstTransition.getTime();
        if (l < l2 || !bl && l == l2) {
            return null;
        }
        Date date = this.stdRule.getPreviousStart(l, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), bl);
        Date date2 = this.dstRule.getPreviousStart(l, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), bl);
        if (date != null && (date2 == null || date.after(date2))) {
            return new TimeZoneTransition(date.getTime(), this.dstRule, this.stdRule);
        }
        if (date2 != null && (date == null || date2.after(date))) {
            return new TimeZoneTransition(date2.getTime(), this.stdRule, this.dstRule);
        }
        return null;
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        this.initTransitionRules();
        int n = this.useDaylight ? 3 : 1;
        TimeZoneRule[] timeZoneRuleArray = new TimeZoneRule[n];
        timeZoneRuleArray[0] = this.initialRule;
        if (this.useDaylight) {
            timeZoneRuleArray[1] = this.stdRule;
            timeZoneRuleArray[2] = this.dstRule;
        }
        return timeZoneRuleArray;
    }

    private synchronized void initTransitionRules() {
        if (this.transitionRulesInitialized) {
            return;
        }
        if (this.useDaylight) {
            DateTimeRule dateTimeRule = null;
            int n = this.startTimeMode == 1 ? 1 : (this.startTimeMode == 2 ? 2 : 0);
            switch (this.startMode) {
                case 1: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startTime, n);
                    break;
                }
                case 2: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, this.startTime, n);
                    break;
                }
                case 3: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, true, this.startTime, n);
                    break;
                }
                case 4: {
                    dateTimeRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, false, this.startTime, n);
                }
            }
            this.dstRule = new AnnualTimeZoneRule(this.getID() + "(DST)", this.getRawOffset(), this.getDSTSavings(), dateTimeRule, this.startYear, Integer.MAX_VALUE);
            long l = this.dstRule.getFirstStart(this.getRawOffset(), 0).getTime();
            n = this.endTimeMode == 1 ? 1 : (this.endTimeMode == 2 ? 2 : 0);
            switch (this.endMode) {
                case 1: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endTime, n);
                    break;
                }
                case 2: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, this.endTime, n);
                    break;
                }
                case 3: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, true, this.endTime, n);
                    break;
                }
                case 4: {
                    dateTimeRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, false, this.endTime, n);
                }
            }
            this.stdRule = new AnnualTimeZoneRule(this.getID() + "(STD)", this.getRawOffset(), 0, dateTimeRule, this.startYear, Integer.MAX_VALUE);
            long l2 = this.stdRule.getFirstStart(this.getRawOffset(), this.dstRule.getDSTSavings()).getTime();
            if (l2 < l) {
                this.initialRule = new InitialTimeZoneRule(this.getID() + "(DST)", this.getRawOffset(), this.dstRule.getDSTSavings());
                this.firstTransition = new TimeZoneTransition(l2, this.initialRule, this.stdRule);
            } else {
                this.initialRule = new InitialTimeZoneRule(this.getID() + "(STD)", this.getRawOffset(), 0);
                this.firstTransition = new TimeZoneTransition(l, this.initialRule, this.dstRule);
            }
        } else {
            this.initialRule = new InitialTimeZoneRule(this.getID(), this.getRawOffset(), 0);
        }
        this.transitionRulesInitialized = true;
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
        SimpleTimeZone simpleTimeZone = (SimpleTimeZone)super.cloneAsThawed();
        simpleTimeZone.isFrozen = false;
        return simpleTimeZone;
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
        $assertionsDisabled = !SimpleTimeZone.class.desiredAssertionStatus();
        staticMonthLength = new byte[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }
}

